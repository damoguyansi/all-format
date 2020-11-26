package com.damoguyansi.all.format.util;

import org.jsoup.Jsoup;

public class HtmlFormat {

    public static String format(String src) {
        org.jsoup.nodes.Document doc = Jsoup.parse(src);
        doc.outputSettings().indentAmount(4);
        return doc.html();
    }
}