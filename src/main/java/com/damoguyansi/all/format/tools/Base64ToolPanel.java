package com.damoguyansi.all.format.tools;

import com.google.common.io.BaseEncoding;

import java.nio.charset.StandardCharsets;

/**
 * Base64 编码 / 解码。
 *
 * @author damoguyansi
 */
public class Base64ToolPanel extends AbstractToolPanel {

    public Base64ToolPanel() {
        addButton("编码", () -> output.setText(
                BaseEncoding.base64().encode(inText().getBytes(StandardCharsets.UTF_8))));
        addButton("解码", () -> output.setText(
                new String(BaseEncoding.base64().decode(inText()), StandardCharsets.UTF_8)));
        addButton("结果→输入", () -> input.setText(output.getText()));
    }

    @Override
    public String title() {
        return "Base64";
    }
}
