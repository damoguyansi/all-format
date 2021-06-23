package com.damoguyansi.all.format.util;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlFormat {

    public static String format(String html) {
        if(StringUtils.isNotBlank(html)) {
            try {
                Document doc = Jsoup.parseBodyFragment(html);
                doc.outputSettings().indentAmount(4);
                html = doc.body().html();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return html;
    }
}
