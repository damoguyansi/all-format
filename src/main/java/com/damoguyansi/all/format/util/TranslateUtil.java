package com.damoguyansi.all.format.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.damoguyansi.all.format.translate.action.GoogleTranslateResult;
import com.damoguyansi.all.format.translate.constant.TranslateConstant;
import com.damoguyansi.all.format.translate.util.GsonUtil;
import com.damoguyansi.all.format.translate.util.TkUtil;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * 翻译工具类
 *
 * @author damoguyansi
 */
public class TranslateUtil {

    private final static Logger logger = LoggerFactory.getLogger(TranslateUtil.class);

    /**
     * 处理返回的错误信息
     *
     * @param message - 错误信息
     * @return
     */
    public static String trans(String message) {
        if (message == null || message == "") {
            return "未知的信息!";
        }
        JSONObject data = JSONObject.parseObject(google(message));
        if (data == null) {
            return "未知的信息!";
        }
        JSONArray sentences = JSONArray.parseArray(data.get("sentences").toString());
        JSONObject trans = JSONObject.parseObject(JSON.toJSONString(sentences.get(0)));
        return trans.getString("trans");
    }

    /**
     * 谷歌翻译
     *
     * @param text - 文本
     * @return
     */
    private static String google(String text) {
        if (text.contains(" ")) {
            text.replace(" ", "%20");
        }
        text = text.replaceAll("\r|\n", "");
        String toLang = isToLang(text);
        final String url = "http://translate.google.cn/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=auto&tl=" + toLang + "&q=";
        System.out.println(url);
        String data = null;
        try {
            data = Jsoup.connect(url + text).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            logger.info("Google.translate Error!");
        }
        return data;
    }

    private static String isToLang(String text) {
        if (isConrZN(text))
            return "en";
        else if (isConrEn(text)) {
            return "zh_cn";
        } else {
            return "en";
        }
    }

    public static boolean isConrEn(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 0x0000 && str.charAt(i) <= 0x00FF)
                continue;
            return false;
        }
        return true;
    }

    public static boolean isConrZN(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 0x0391 && str.charAt(i) <= 0xFFE5)
                continue;
            return false;
        }
        return true;
    }

    /**
     * 调用接口,翻译并返回值
     *
     * @param word
     * @param translateType
     * @return
     * @throws Exception
     */
    public static GoogleTranslateResult translate(String word, String translateType) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        //replace填坑参数地址值
        String url = getTranslateUrl(translateType, TkUtil.tk(word), URLEncoder.encode(word, "utf-8"));
        System.out.println("translate url:" + url);
        HttpGet get = new HttpGet(url);
        get.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        get.setHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9");

        //发起请求
        CloseableHttpResponse response = client.execute(get);
        if (200 == response.getStatusLine().getStatusCode()) {
            String responseText = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(responseText);
            GoogleTranslateResult translateResult = GsonUtil.jsonToObject(responseText, GoogleTranslateResult.class);
            return translateResult;
        }
        return null;
    }

    /**
     * 根据类型选择接口地址
     *
     * @param translateType
     * @return
     */
    private static String getTranslateUrl(String translateType, String tk, String word) {
        if (translateType.equals(TranslateConstant.ZH_CN_TO_EN)) {
            return String.format(TranslateConstant.GOOGLE_TRANSLATE_URL, "zh-CN", "en", tk, word);
        } else {
            return String.format(TranslateConstant.GOOGLE_TRANSLATE_URL, "en", "zh-CN", tk, word);
        }
    }
}
