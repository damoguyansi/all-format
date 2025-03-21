package com.damoguyansi.all.format.translate.baidu;

import java.util.List;

public class BaiDuTransResponse {

    // 原文语种
    private String from;

    // 目标语种
    private String to;

    // 翻译结果
    private List<BaiDuTransResult> trans_result;

    // 错误编码, 当报错时才有值
    private String error_code;

    // 错误信息, 当报错时才有值
    private String error_msg;

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

    public List<BaiDuTransResult> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<BaiDuTransResult> trans_result) {
        this.trans_result = trans_result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}