package com.damoguyansi.all.format.translate.constant;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author damoguyansi
 * @description: 翻译功能常量
 * @create: 2020-06-30 16:10
 **/
public class TranslateConstant {
    /**
     * 中文正则
     */
    public static final Pattern p = compile("[\u4e00-\u9fa5]");
    /**
     * 中译英
     */
    public static final String ZH_CN_TO_EN = "zhCnToEn";

    /**
     * 英译中
     */
    public static final String EN_TO_ZH_CN = "enToZhCn";

    /**
     * 谷歌翻译地址
     */
    public static final String GOOGLE_TRANSLATE_URL = "https://translate.google.cn/translate_a/single?client=gtx&dt=t&dt=bd&dt=rm&dj=1&ie=UTF-8&oe=UTF-8&sl=%s&tl=%s&hl=zh-CN&tk=%s&q=%s";
}
