package com.damoguyansi.all.format.util;

import com.damoguyansi.all.format.translate.TranslationService;
import com.damoguyansi.all.format.translate.bean.TransResult;

import java.util.regex.Pattern;

/**
 * 翻译工具类：仅负责语种识别与对外便捷入口，具体引擎调用委托给
 * {@link TranslationService}。
 *
 * @author damoguyansi
 */
public class TranslateUtil {
    /**
     * 中文正则
     */
    public static final Pattern p = Pattern.compile("[一-龥]");

    /**
     * 中译英
     */
    public static final String ZH_CN_TO_EN = "zhCnToEn";

    /**
     * 英译中
     */
    public static final String EN_TO_ZH_CN = "enToZhCn";

    private TranslateUtil() {
    }

    /**
     * 根据文本内容自动判断翻译方向：含中文则中译英，否则英译中。
     *
     * @param text 待翻译文本
     * @return 翻译方向常量
     */
    public static String detectType(String text) {
        return p.matcher(text).find() ? ZH_CN_TO_EN : EN_TO_ZH_CN;
    }

    /**
     * 自动识别语种并翻译（保留旧签名以兼容历史调用）。
     */
    public static TransResult translate(String word, String translateType) throws Exception {
        return TranslationService.translate(word, translateType);
    }
}
