package com.damoguyansi.all.format.util;

public class HtmlUtil {

    public static final String AMP = "&amp;";
    public static final String QUOTE = "&quot;";
    public static final String LT = "&lt;";
    public static final String GT = "&gt;";

    private static final char[][] TEXT = new char[64][];

    static {
        for (int i = 0; i < 64; i++) {
            TEXT[i] = new char[]{(char) i};
        }

        // special HTML characters
        TEXT['\''] = "&#039;".toCharArray(); // 单引号 ('&apos;' doesn't work - it is not by the w3 specs)
        TEXT['"'] = QUOTE.toCharArray(); // 单引号
        TEXT['&'] = AMP.toCharArray(); // &符
        TEXT['<'] = LT.toCharArray(); // 小于号
        TEXT['>'] = GT.toCharArray(); // 大于号
    }

    public static String escape(String text) {
        int len;
        if ((text == null) || ((len = text.length()) == 0)) {
            return "";
        }
        StringBuilder buffer = new StringBuilder(len + (len >> 2));
        char c;
        for (int i = 0; i < len; i++) {
            c = text.charAt(i);
            if (c < 64) {
                buffer.append(TEXT[c]);
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }
}
