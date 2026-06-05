package com.damoguyansi.all.format.translate;

import com.damoguyansi.all.format.translate.bean.TransResult;
import com.damoguyansi.all.format.translate.microsoft.MicrosoftTransApi;
import com.damoguyansi.all.format.util.TranslateUtil;

/**
 * 翻译服务统一入口：负责语种自动识别并委托给翻译引擎。
 * <p>
 * 当前仅启用微软翻译引擎；如需扩展其它引擎，实现 {@link TransApiInterface}
 * 并在此处替换 {@link #ENGINE} 即可。
 *
 * @author damoguyansi
 */
public final class TranslationService {

    private static final TransApiInterface ENGINE = new MicrosoftTransApi();

    private TranslationService() {
    }

    /** 自动识别语种并翻译。 */
    public static TransResult translate(String word) throws Exception {
        return translate(word, TranslateUtil.detectType(word));
    }

    /** 指定翻译方向翻译。 */
    public static TransResult translate(String word, String translateType) throws Exception {
        TransResult result = ENGINE.translate(word, translateType);
        if (result == null || result.getSentences() == null || result.getSentences().isEmpty()) {
            throw new Exception("翻译失败：无结果");
        }
        return result;
    }
}
