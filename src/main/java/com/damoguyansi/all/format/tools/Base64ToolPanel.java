package com.damoguyansi.all.format.tools;

import com.damoguyansi.all.format.i18n.I18n;
import com.google.common.io.BaseEncoding;

import java.nio.charset.StandardCharsets;

/**
 * Base64 编码 / 解码。
 *
 * @author damoguyansi
 */
public class Base64ToolPanel extends AbstractToolPanel {

    public Base64ToolPanel() {
        addButton(I18n.message("common.encode"), () -> output.setText(
                BaseEncoding.base64().encode(inText().getBytes(StandardCharsets.UTF_8))));
        addButton(I18n.message("common.decode"), () -> output.setText(
                new String(BaseEncoding.base64().decode(inText()), StandardCharsets.UTF_8)));
        addButton(I18n.message("common.resultToInput"), () -> input.setText(output.getText()));
    }

    @Override
    public String title() {
        return "Base64";
    }
}
