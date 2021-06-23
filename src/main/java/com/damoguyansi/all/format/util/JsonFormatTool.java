package com.damoguyansi.all.format.util;

import com.google.gson.*;

public class JsonFormatTool {
    public static Gson gson;
    public static Gson prettyGson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = gsonBuilder.create();
        GsonBuilder prettyGsonBuilder = new GsonBuilder();
        prettyGsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        prettyGson = prettyGsonBuilder.setPrettyPrinting().create();
    }

    /**
     * json 转对象，可以兼容不认识的属性
     *
     * @param json
     * @param objectType
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> objectType) {
        return gson.fromJson(json, objectType);
    }

    /**
     * 格式化
     *
     * @param json
     * @return
     */
    public static String format(String json) {
        json = unescape(json);
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();
            return prettyGson.toJson(jsonArray);
        } else if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
            return prettyGson.toJson(jsonObject);
        } else {
            throw new RuntimeException("Json 格式有误");
        }
    }

    /**
     * 压缩
     */
    public static String compress(String json) {
        json = unescape(json);
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(json);
        return gson.toJson(je);
    }

    public static String escape(String json) {
        return json.replaceAll("\"", "\\\\\"");
    }

    public static String unescape(String json) {
        return json.replaceAll("\\\\\"", "\"");
    }
}
