package com.damoguyansi.all.format.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
        return trans.getString("trans").replace(" ", "");
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
        final String url = "http://translate.google.cn/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=auto&tl=zh_cn&q=";
        String data = null;
        try {
            data = Jsoup.connect(url + text).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            logger.info("Google.translate Error!");
        }
        return data;
    }

}