package com.damoguyansi.all.format.util;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;

public class SqlFormat {

    public static String format(String sql) {
        try {
            String result = SQLUtils.format(sql, JdbcConstants.MYSQL);
            String resultCase = SQLUtils.format(result, JdbcConstants.HIVE, SQLUtils.DEFAULT_LCASE_FORMAT_OPTION);
            return resultCase;
        } catch (Exception e) {
            return null;
        }
    }
}