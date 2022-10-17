package com.damoguyansi.all.format.translate.baidu;

import java.util.HashMap;
import java.util.Map;

public class BDTransRequest {

    // 翻译源语种, 默认为中文
    private String from = "zh";

    // 目标语种, 默认为英文
    private String to = "en";

    // 百度翻译平台注册id
    private String appid;

    // 随机数
    private String salt;

    // 翻译原文, UTF-8编码
    private String q;

    // 数字签名=md5(appid+q+salt+密钥)
    private String sign;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("q", this.q);
        map.put("sign", this.sign);
        map.put("appid", this.appid);
        map.put("from", this.from);
        map.put("to", this.to);
        map.put("salt", this.salt);
        return map;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}