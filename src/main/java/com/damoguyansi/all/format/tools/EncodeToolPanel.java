package com.damoguyansi.all.format.tools;

import cn.hutool.core.util.URLUtil;
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
        addButton("Unicode 编码", () -> output.setText(UnicodeUtil.unicodeEncode(inText())));
        addButton("Unicode 解码", () -> output.setText(UnicodeUtil.unicodeDecode(inText())));
        addButton("URL 编码", () -> output.setText(URLUtil.encode(inText())));
        addButton("URL 解码", () -> output.setText(URLUtil.decode(inText())));
        addButton("MD5", () -> output.setText(MD5Util.md5(inText()).toUpperCase(Locale.ROOT)));
        addButton("结果→输入", () -> input.setText(output.getText()));
    }

    @Override
    public String title() {
        return "Encode";
    }
}
