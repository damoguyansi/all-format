package com.damoguyansi.all.format.util;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author damoguyansi
 * @description: 字符串工具类
 * @create: 2020-06-29 11:43
 **/
public class WordUtil {

    private final static char UNDERSCORE = '_';

    /**
     * 字符串转换成常量
     *
     * @param str
     * @return
     */
    public static String textToConstant(String str) {
        return textToWords(str).replaceAll("\\s+", "_").toUpperCase();
    }

    /**
     * 单词转下划线命名
     *
     * @param str
     * @return
     */
    public static String textToUnderscoreCase(String str) {
        return textToWords(str).replaceAll("\\s+", "_");
    }

    /**
     * 单词转短横线命名
     *
     * @param str
     * @return
     */
    public static String textToKebabCase(String str, boolean isUpperKebab) {
        str = textToWords(str).replaceAll("\\s+", "-");
        if (isUpperKebab) {
            str = ((char) (str.charAt(0) - 32)) + str.substring(1);
        }
        return str;
    }

    /**
     * 单词转大小驼峰命名
     *
     * @param str
     * @param isUpperCamel true 大驼峰 false 小驼峰
     * @return
     */
    public static String textToCamelCase(String str, boolean isUpperCamel) {
        str = textToWords(str);
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (i == 0 && isUpperCamel) {
                stringBuilder.append(Character.toUpperCase(chars[i]));
                continue;
            }
            if (chars[i] == ' ') {
                stringBuilder.append(Character.toUpperCase(chars[++i]));
            } else {
                stringBuilder.append(chars[i]);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 拆分单词
     *
     * @param str
     * @return
     */
    public static String textToWords(String str) {
        //selectText  select Text select_text select_Text SELECT_TEXT
        str = str.replaceAll("[-|_|\\s|\n|\r]+", " ");
        if (isAllUpperCase(str, ' ')) {
            return str.toLowerCase();
        }
        char c;
        char previousChar = ' ';
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, len = str.length(); i < len; i++) {
            c = str.charAt(i);
            if (i != 0 && i + 1 < str.length() && Character.isUpperCase(c) && Character.isLowerCase(str.charAt(i + 1))) {
                stringBuilder.append(" ").append(c);
            } else if (Character.isLowerCase(previousChar) && Character.isUpperCase(c)) {
                stringBuilder.append(" ").append(c);
            } else {
                stringBuilder.append(c);
            }
            previousChar = c;
        }
        return stringBuilder.toString().trim().toLowerCase();
    }

    /**
     * 除了指定字符全是大写
     *
     * @param str
     * @return
     */
    private static boolean isAllUpperCase(String str, Character... characters) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (!Character.isUpperCase(c)) {
                boolean flag = false;
                for (Character character : characters) {
                    if (c == character) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取所有格式的字符串
     *
     * @param text
     * @return
     */
    public static LinkedHashSet<String> getAllCase(String text) {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add(WordUtil.textToCamelCase(text, false));
        set.add(WordUtil.textToCamelCase(text, true));
        set.add(WordUtil.textToConstant(text));
        set.add(WordUtil.textToUnderscoreCase(text));
        set.add(WordUtil.textToKebabCase(text, false));
        set.add(WordUtil.textToKebabCase(text, true));
        set.add(WordUtil.textToWords(text));
        set.add(text.toUpperCase());
        set.add(text.toLowerCase());
        return set;
    }

    /**
     * 获取所有格式的字符串(用于翻译结果)
     *
     * @param text
     * @return
     */
    public static Set<String> getAllTranslateCase(String text) {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add(WordUtil.textToCamelCase(text, false));
        set.add(WordUtil.textToCamelCase(text, true));
        set.add(WordUtil.textToConstant(text));
        set.add(WordUtil.textToUnderscoreCase(text));
        set.add(WordUtil.textToKebabCase(text, false));
        set.add(WordUtil.textToKebabCase(text, true));
        return set;
    }
}
