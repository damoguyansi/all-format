package com.damoguyansi.all.format.tools;

import com.damoguyansi.all.format.i18n.I18n;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

/**
 * 工具面板基类：上方输入、中间按钮行、下方只读输出，统一扁平外观。
 *
 * @author damoguyansi
 */
public abstract class AbstractToolPanel extends JPanel implements ToolPanel {

    protected final JBTextArea input = new JBTextArea();
    protected final JBTextArea output = new JBTextArea();
    private final JPanel buttonRow = ToolUi.toolbar();

    protected AbstractToolPanel() {
        super(new BorderLayout(0, 4));
        setBorder(JBUI.Borders.empty(6));

        input.setLineWrap(true);
        input.setRows(4);
        output.setLineWrap(true);
        output.setEditable(false);

        JBScrollPane inScroll = new JBScrollPane(input);
        JBScrollPane outScroll = new JBScrollPane(output);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inScroll, outScroll);
        split.setResizeWeight(0.4);
        split.setBorder(JBUI.Borders.empty());

        add(buttonRow, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
    }

    /** 在按钮行追加任意组件（如标签、输入框）。 */
    protected void addControl(Component c) {
        buttonRow.add(c);
    }

    /** 添加一个操作按钮。 */
    protected void addButton(String text, Runnable action) {
        buttonRow.add(ToolUi.button(text, () -> {
            try {
                action.run();
            } catch (Exception ex) {
                output.setText(I18n.message("common.error", ex.getMessage()));
            }
        }));
    }

    protected String inText() {
        return input.getText() == null ? "" : input.getText().trim();
    }

    @Override
    public JComponent component() {
        return this;
    }
}
