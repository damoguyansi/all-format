package com.damoguyansi.all.format.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringEscapeUtils;

public class JsonFormatTool {

    public static String format(String text) throws Exception {
        String result = null;
        JsonElement jsonEle = null;

        Object obj = JSON.parse(text);
        text = JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
        jsonEle = JsonParser.parseString(text);
        if (jsonEle != null && !jsonEle.isJsonNull()) {
            GsonBuilder gb = new GsonBuilder();
            gb.setPrettyPrinting();
            gb.serializeNulls();
            Gson gson = gb.create();
            result = gson.toJson(jsonEle);
            if (result != null) {
                result = StringEscapeUtils.unescapeJava(result);
            }
        } else {
            throw new Exception("非法JSON字符串！");
        }
        return result;
    }
}