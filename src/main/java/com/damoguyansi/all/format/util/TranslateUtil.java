package com.damoguyansi.all.format.util;

import com.damoguyansi.all.format.translate.bean.GTResult;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 翻译工具类
 *
 * @author damoguyansi
 */
public class TranslateUtil {
    /**
     * 中文正则
     */
    public static final Pattern p = compile("[\u4e00-\u9fa5]");
    /**
     * 中译英
     */
    public static final String ZH_CN_TO_EN = "zhCnToEn";

    /**
     * 英译中
     */
    public static final String EN_TO_ZH_CN = "enToZhCn";

    /**
     * 谷歌翻译地址
     */
    public static final String GOOGLE_TRANSLATE_URL = "https://translate.google.cn/translate_a/single?client=gtx&dt=t&dt=bd&dt=rm&dj=1&ie=UTF-8&oe=UTF-8&sl=%s&tl=%s&hl=zh-CN&tk=%s&q=%s";

    private static final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000)
            .setSocketTimeout(5000).setConnectTimeout(5000).build();

    /**
     * 调用接口,翻译并返回值
     *
     * @param word
     * @param translateType
     * @return
     * @throws Exception
     */
    public static GTResult translate(String word, String translateType) throws Exception {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            String url = getTranslateUrl(translateType, TkUtil.tk(word), URLEncoder.encode(word, "utf-8"));
            System.out.println("translate url:" + url);
            HttpGet get = new HttpGet(url);
            get.setConfig(requestConfig);
            get.setHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
            get.setHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9");
            //发起请求
            CloseableHttpResponse response = client.execute(get);
            if (200 == response.getStatusLine().getStatusCode()) {
                String responseText = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println("translate result:" + responseText);
                GTResult translateResult = JsonFormatTool.jsonToObject(responseText, GTResult.class);
                return translateResult;
            } else {
                throw new Exception("Google翻译请求频繁限制！");
            }
        } catch (UnknownHostException e) {
            throw new Exception("你没连接网络吧！");
        } catch (Exception e) {
            throw new Exception("请求Google翻译异常!");
        }
    }

    /**
     * 根据类型选择接口地址
     *
     * @param translateType
     * @param tk
     * @param word
     * @return
     */
    private static String getTranslateUrl(String translateType, String tk, String word) {
        if (translateType.equals(ZH_CN_TO_EN)) {
            return String.format(GOOGLE_TRANSLATE_URL, "zh-CN", "en", tk, word);
        } else {
            return String.format(GOOGLE_TRANSLATE_URL, "en", "zh-CN", tk, word);
        }
    }
}
