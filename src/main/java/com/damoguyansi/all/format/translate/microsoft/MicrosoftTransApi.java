package com.damoguyansi.all.format.translate.microsoft;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.damoguyansi.all.format.translate.TransApiInterface;
import com.damoguyansi.all.format.translate.bean.TransResult;
import com.damoguyansi.all.format.i18n.I18n;
import com.damoguyansi.all.format.util.TranslateUtil;
import com.intellij.util.io.HttpRequests;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 微软翻译引擎。
 * <p>
 * 使用 Edge 浏览器公开的免鉴权端点：先从 {@code edge.microsoft.com/translate/auth}
 * 获取临时 Bearer Token，再调用 {@code api-edge.cognitive.microsofttranslator.com} 的
 * translate 与 dictionary/lookup 接口，从而拿到主译文、发音（音标/转写）以及
 * 按词性归类的其它释义。网络请求统一走 IntelliJ 平台自带的 {@link HttpRequests}。
 *
 * @author damoguyansi
 */
public class MicrosoftTransApi implements TransApiInterface {

    private static final String AUTH_URL = "https://edge.microsoft.com/translate/auth";
    private static final String API_HOST = "https://api-edge.cognitive.microsofttranslator.com";
    private static final String TRANSLATE_URL = API_HOST + "/translate?api-version=3.0&from=%s&to=%s";
    private static final String DICT_URL = API_HOST + "/dictionary/lookup?api-version=3.0&from=%s&to=%s";

    private static final int TIMEOUT = 5000;
    private static final long TOKEN_TTL = 8 * 60 * 1000L;

    /** 进程内 Token 缓存，避免每次翻译都重新鉴权。 */
    private static volatile String cachedToken;
    private static volatile long tokenTime;

    @Override
    public TransResult translate(String word, String translateType) throws Exception {
        boolean zhToEn = TranslateUtil.ZH_CN_TO_EN.equals(translateType);
        String from = zhToEn ? "zh-Hans" : "en";
        String to = zhToEn ? "en" : "zh-Hans";

        String token = token();
        String body = new JSONArray().put(new JSONObject().set("Text", word)).toString();

        TransResult result = new TransResult();
        result.setSrc(word);
        result.setSentences(translateText(token, from, to, body, word));
        // 词典释义为可选增强：失败不影响主译文
        result.setDict(lookupDictionary(token, from, to, body));
        return result;
    }

    /** 主译文 + 目标语转写（如中文拼音）。 */
    private List<TransResult.SentencesBean> translateText(String token, String from, String to,
                                                          String body, String word) throws Exception {
        String resp = post(String.format(TRANSLATE_URL, from, to), token, body);

        JSONArray arr = JSONUtil.parseArray(resp);
        if (arr.isEmpty()) throw new Exception(I18n.message("translate.microsoftNoResult"));

        JSONArray translations = arr.getJSONObject(0).getJSONArray("translations");
        if (translations == null || translations.isEmpty()) {
            throw new Exception(I18n.message("translate.microsoftNoResult"));
        }

        JSONObject t0 = translations.getJSONObject(0);
        TransResult.SentencesBean bean = new TransResult.SentencesBean();
        bean.setOrig(word);
        bean.setTrans(t0.getStr("text"));
        JSONObject translit = t0.getJSONObject("transliteration");
        if (translit != null) {
            bean.setTranslit(translit.getStr("text"));
        }
        List<TransResult.SentencesBean> sentences = new ArrayList<>();
        sentences.add(bean);
        return sentences;
    }

    /** 词典查询：按词性归类的其它释义与反向翻译。失败时返回空，不抛出。 */
    private List<TransResult.DictBean> lookupDictionary(String token, String from, String to, String body) {
        List<TransResult.DictBean> dicts = new ArrayList<>();
        try {
            String resp = post(String.format(DICT_URL, from, to), token, body);
            JSONArray arr = JSONUtil.parseArray(resp);
            if (arr.isEmpty()) return dicts;

            JSONArray translations = arr.getJSONObject(0).getJSONArray("translations");
            if (translations == null) return dicts;

            LinkedHashMap<String, TransResult.DictBean> byPos = new LinkedHashMap<>();
            for (Object o : translations) {
                JSONObject t = (JSONObject) o;
                String pos = t.getStr("posTag", "OTHER");
                TransResult.DictBean dict = byPos.computeIfAbsent(pos, p -> {
                    TransResult.DictBean d = new TransResult.DictBean();
                    d.setPos(p);
                    d.setEntry(new ArrayList<>());
                    return d;
                });
                TransResult.DictBean.EntryBean entry = new TransResult.DictBean.EntryBean();
                entry.setWord(t.getStr("displayTarget"));
                entry.setScore(t.getDouble("confidence", 0d));
                List<String> back = new ArrayList<>();
                JSONArray bts = t.getJSONArray("backTranslations");
                if (bts != null) {
                    for (Object bo : bts) {
                        back.add(((JSONObject) bo).getStr("displayText"));
                    }
                }
                entry.setReverseTranslation(back);
                dict.getEntry().add(entry);
            }
            dicts.addAll(byPos.values());
        } catch (Exception ignored) {
            // 词典为可选项，忽略异常
        }
        return dicts;
    }

    private String post(String url, String token, String body) throws Exception {
        return HttpRequests.post(url, "application/json; charset=UTF-8")
                .connectTimeout(TIMEOUT)
                .readTimeout(TIMEOUT)
                .tuner(c -> c.setRequestProperty("Authorization", "Bearer " + token))
                .connect(request -> {
                    request.write(body);
                    return request.readString();
                });
    }

    /** 获取并缓存鉴权 Token。 */
    private String token() throws Exception {
        long now = System.currentTimeMillis();
        if (cachedToken != null && now - tokenTime < TOKEN_TTL) {
            return cachedToken;
        }
        synchronized (MicrosoftTransApi.class) {
            if (cachedToken != null && now - tokenTime < TOKEN_TTL) {
                return cachedToken;
            }
            String fresh = HttpRequests.request(AUTH_URL)
                    .connectTimeout(TIMEOUT)
                    .readTimeout(TIMEOUT)
                    .readString();
            if (fresh == null || fresh.isEmpty()) {
                throw new Exception(I18n.message("translate.microsoftAuthFailed"));
            }
            cachedToken = fresh.trim();
            tokenTime = now;
            return cachedToken;
        }
    }
}
