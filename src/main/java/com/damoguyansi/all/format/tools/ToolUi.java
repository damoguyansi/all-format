package com.damoguyansi.all.format.tools;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

/**
 * 工具面板统一视觉风格：使用平台原生按钮（更紧凑），带分隔线的换行工具栏。
 *
 * @author damoguyansi
 */
public final class ToolUi {

    private ToolUi() {
    }

    /** 平台原生扁平按钮（紧凑）。 */
    public static JButton button(String text, Runnable action) {
        return make(text, action, false);
    }

    /** 主操作按钮（蓝色强调，原生 default 样式）。 */
    public static JButton primaryButton(String text, Runnable action) {
        return make(text, action, true);
    }

    private static JButton make(String text, Runnable action, boolean primary) {
        JButton b = new JButton(text);
        b.setFocusable(false);
        b.setFont(JBFont.medium());
        if (primary) {
            b.putClientProperty("JButton.buttonType", "default");
        }
        if (action != null) {
            b.addActionListener(e -> action.run());
        }
        return b;
    }

    /** 顶部换行工具栏：底部一条细分隔线。 */
    public static JPanel toolbar() {
        JPanel bar = new JPanel(new WrapLayout(FlowLayout.LEFT, 4, 2));
        bar.setBorder(BorderFactory.createCompoundBorder(
                JBUI.Borders.customLine(separatorColor(), 0, 0, 1, 0),
                JBUI.Borders.empty(2, 0, 4, 0)));
        return bar;
    }

    private static Color separatorColor() {
        return JBColor.namedColor("Group.separatorColor", JBColor.border());
    }
}
