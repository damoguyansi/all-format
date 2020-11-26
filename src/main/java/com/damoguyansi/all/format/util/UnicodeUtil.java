package com.damoguyansi.all.format.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeUtil {

    public static String unicodeEncode(String string) {
        try {
            char[] utfBytes = string.toCharArray();
            String unicodeBytes = "";
            for (int i = 0; i < utfBytes.length; i++) {
                String hexB = Integer.toHexString(utfBytes[i]);
                if (hexB.length() <= 2) {
                    hexB = "00" + hexB;
                }
                unicodeBytes = unicodeBytes + "\\u" + hexB;
            }
            return unicodeBytes;
        } catch (Exception e) {
            return null;
        }
    }

    public static String unicodeDecode(String string) {
        try {
            Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
            Matcher matcher = pattern.matcher(string);
            char ch;
            while (matcher.find()) {
                ch = (char) Integer.parseInt(matcher.group(2), 16);
                string = string.replace(matcher.group(1), ch + "");
            }
            return string;
        } catch (Exception e) {
            return null;
        }
    }
}
