package com.damoguyansi.all.format.util;

import com.damoguyansi.all.format.sql.BasicFormatterImpl;
import com.damoguyansi.all.format.sql.Formatter;

public class SqlFormat {

    private static Formatter format = new BasicFormatterImpl();

    public static String format(String sql) {
        try {
            return format.format(sql);
        } catch (Exception e) {
            return null;
        }
    }
}