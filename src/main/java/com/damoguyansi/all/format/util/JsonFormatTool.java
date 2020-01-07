package com.damoguyansi.all.format.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonFormatTool {

    public static void main(String[] args) {
        String compactJson = "{\"playerID\":1234,\"name\":\"Test\",\"itemList\":[{\"itemID\":1,\"name\":\"Axe\",\"atk\":12,\"def\":0},{\"itemID\":2,\"name\":\"Sword\",\"atk\":5,\"def\":5},{\"itemID\":3,\"name\":\"Shield\",\"atk\":0,\"def\":10}]}";
        String prettyJson = format(compactJson);
        System.out.println("compactJson:");
        System.out.println(compactJson);
        System.out.println("prettyJson:");
        System.out.println(prettyJson);
    }

    public static String format(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }
}