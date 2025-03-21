package com.damoguyansi.all.format.util;

import com.damoguyansi.all.format.constant.Constants;

/**
 * desc:
 *
 * @author damoguyansi
 * @date 2018/7/18 15:49
 */
public class MapFormat {
    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append(Constants.PER_SPACE);
        }
        return levelStr.toString();
    }

    public static String format(String src) {
        src = src.replaceAll("\n[ ]+", "");
        int level = 0;
        StringBuffer resStr = new StringBuffer();
        for (int index = 0; index < src.length(); index++) {
            char c = src.charAt(index);
            if (level > 0 && '\n' == resStr.charAt(resStr.length() - 1)) {
                resStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    resStr.append(c + "\n");
                    level++;
                    break;
                case '&':
                case ',':
                    resStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    resStr.append("\n");
                    level--;
                    resStr.append(getLevelStr(level));
                    resStr.append(c);
                    break;
                default:
                    resStr.append(c);
                    break;
            }
        }
        return resStr.toString();
    }
}
