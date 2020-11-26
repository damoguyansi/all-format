package com.damoguyansi.all.format.cache;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

/**
 * 配置文件缓存
 *
 * @date 2020.11.24
 */
public class ParamCache {
    private static final String BASE_DIR = System.getProperty("java.io.tmpdir");
    private static final String CACHE_FILE_NAME = "allformat.json";
    private JsonObject paramObj = null;

    public ParamCache() {
        File file = new File(BASE_DIR, CACHE_FILE_NAME);
        System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            String params = readFile(file);
            if (null != params && !"".equals(params))
                paramObj = JsonParser.parseString(params).getAsJsonObject();
            else
                paramObj = new JsonObject();
        }
    }

    private String readFile(File file) {
        String encoding = "UTF-8";
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        if (null == paramObj)
            return;

        try {
            File writeName = new File(BASE_DIR, CACHE_FILE_NAME);
            writeName.createNewFile();
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(paramObj.toString());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean readAsBoolean(CacheName cacheName) {
        if (null != paramObj && null != paramObj.get(cacheName.getName())) {
            return paramObj.get(cacheName.getName()).getAsBoolean();
        }
        return null;
    }

    public Integer readAsInteger(CacheName cacheName) {
        if (null != paramObj && null != paramObj.get(cacheName.getName())) {
            return paramObj.get(cacheName.getName()).getAsInt();
        }
        return null;
    }

    public String readAsString(CacheName cacheName) {
        if (null != paramObj && null != paramObj.get(cacheName.getName())) {
            return paramObj.get(cacheName.getName()).getAsString();
        }
        return null;
    }

    public void writeByName(CacheName cacheName, String value) {
        if (null == paramObj)
            paramObj = new JsonObject();
        paramObj.addProperty(cacheName.getName(), value);
    }

    public static void main(String[] args) throws Exception {
        ParamCache pc = new ParamCache();
        Boolean onTop = pc.readAsBoolean(CacheName.ON_TOP);
        System.out.println(onTop);
        pc.writeByName(CacheName.ON_TOP, Boolean.toString(null == onTop ? false : !onTop));

        pc.close();
    }
}
