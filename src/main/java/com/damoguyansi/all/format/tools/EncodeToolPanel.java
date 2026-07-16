package com.damoguyansi.all.format.tools;

import cn.hutool.core.util.URLUtil;
import com.damoguyansi.all.format.i18n.I18n;
import com.damoguyansi.all.format.util.MD5Util;
import com.damoguyansi.all.format.util.UnicodeUtil;

import java.util.Locale;

/**
 * Unicode / URL 编解码与 MD5。工具栏按钮较多，窄窗自动换行。
 *
 * @author damoguyansi
 */
public class EncodeToolPanel extends AbstractToolPanel {

    public EncodeToolPanel() {
        addButton(I18n.message("encode.unicodeEncode"), () -> output.setText(UnicodeUtil.unicodeEncode(inText())));
        addButton(I18n.message("encode.unicodeDecode"), () -> output.setText(UnicodeUtil.unicodeDecode(inText())));
        addButton(I18n.message("encode.urlEncode"), () -> output.setText(URLUtil.encode(inText())));
        addButton(I18n.message("encode.urlDecode"), () -> output.setText(URLUtil.decode(inText())));
        addButton("MD5", () -> output.setText(MD5Util.md5(inText()).toUpperCase(Locale.ROOT)));
        addButton(I18n.message("common.resultToInput"), () -> input.setText(output.getText()));
    }

    @Override
    public String title() {
        return "Encode";
    }
}
