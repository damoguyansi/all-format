package com.damoguyansi.all.format.util;

import cn.hutool.json.JSONUtil;

/**
 * 说明：
 *
 * @author damoguyansi
 * @date 2022/10/17
 */
public class FormatUtil {
    public static String format(String json) {
        json = json.replaceAll("\\\\\"", "\"");
        return JSONUtil.toJsonPrettyStr(json);
    }
}
