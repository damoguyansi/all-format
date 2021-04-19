package com.damoguyansi.all.format.cache;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.*;

/**
 * 配置文件缓存
 *
 * @date 2020.11.24
 */
public class ParamCache {
    private static final String BASE_DIR = System.getProperty("java.io.tmpdir");
    private static final String CACHE_FILE_NAME = "allformat.json";
    private JSONObject paramObj = null;

    public ParamCache() {
        File file = new File(BASE_DIR, CACHE_FILE_NAME);
        if (file.exists()) {
            String params = readFile(file);
            if (null != params && !"".equals(params))
                paramObj = JSONUtil.parseObj(params);
            else
                paramObj = new JSONObject();
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
        try {
            if (null != paramObj && null != paramObj.get(cacheName.getName())) {
                return paramObj.getBool(cacheName.getName(), false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + ":" + BASE_DIR + "/" + CACHE_FILE_NAME);
        }
        return null;
    }

    public Integer readAsInteger(CacheName cacheName) {
        if (null != paramObj && null != paramObj.get(cacheName.getName())) {
            return paramObj.getInt(cacheName.getName(), 0);
        }
        return null;
    }

    public String readAsString(CacheName cacheName) {
        try {
            if (null != paramObj && null != paramObj.get(cacheName.getName())) {
                return paramObj.getStr(cacheName.getName(), null);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + ":" + BASE_DIR + "/" + CACHE_FILE_NAME);
        }
        return null;
    }

    public void writeByName(CacheName cacheName, String value) {
        if (null == paramObj)
            paramObj = new JSONObject();
        paramObj.putOpt(cacheName.getName(), value);
    }

    public static void main(String[] args) throws Exception {
//        ParamCache pc = new ParamCache();
//        Boolean onTop = pc.readAsBoolean(CacheName.ON_TOP);
//        System.out.println(onTop);
//        pc.writeByName(CacheName.ON_TOP, Boolean.toString(null == onTop ? false : !onTop));
//
//        pc.close();
        System.out.println(BASE_DIR);
    }
}
