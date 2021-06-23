package com.damoguyansi.all.format.util;

import java.util.List;

public class CollUtil {

    public static <T> String join(List<String> list, String conjunction) {
        if (null == list) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        list.forEach(s -> {
            sb.append(s).append(conjunction);
        });
        return sb.toString();
    }
}
