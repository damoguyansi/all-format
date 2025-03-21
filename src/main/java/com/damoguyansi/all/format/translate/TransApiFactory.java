package com.damoguyansi.all.format.translate;

import com.damoguyansi.all.format.translate.baidu.BaiDuTransApi;
import com.damoguyansi.all.format.translate.bean.ApiCode;
import com.damoguyansi.all.format.translate.microsoft.MicrosoftTransApi;

/**
 * 翻译渠道API工厂
 *
 * @author damoguyansi
 */
public class TransApiFactory {
    public static TransApiInterface createApi(String code) {
        if (code.equals(ApiCode.BAIDU)) {
            return new BaiDuTransApi();
        } else if (code.equals(ApiCode.Microsoft)) {
            return new MicrosoftTransApi();
        }
        return null;
    }
}
