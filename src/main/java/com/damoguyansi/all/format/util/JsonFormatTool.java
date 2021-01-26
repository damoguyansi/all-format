package com.damoguyansi.all.format.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonFormatTool {

    public static String format(String text) throws Exception {
        if (null == text || "".equals(text.trim())) {
            return text;
        }
        JsonElement je = JsonParser.parseString(text);
        if (!je.isJsonObject() && !je.isJsonArray()) throw new Exception("非法json格式");
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting();
        gb.serializeNulls();
        Gson gson = gb.create();
        return gson.toJson(je);
    }

}