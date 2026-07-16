package com.damoguyansi.all.format.tools;

import com.damoguyansi.all.format.dialog.FormatOperations;
import com.damoguyansi.all.format.i18n.I18n;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.JBUI;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

/**
 * JSON 格式化 / 压缩，带语法高亮。工具栏自动换行，窄窗不再遮挡按钮。
 *
 * @author damoguyansi
 */
public class JsonToolPanel extends JPanel implements ToolPanel {

    private final RSyntaxTextArea editor = new RSyntaxTextArea();
    private final JBLabel status = new JBLabel(" ");

    public JsonToolPanel() {
        super(new BorderLayout(0, 4));
        setBorder(JBUI.Borders.empty(6));

        editor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
        editor.setCodeFoldingEnabled(true);
        editor.setAntiAliasingEnabled(true);
        applyTheme();

        JPanel bar = ToolUi.toolbar();
        bar.add(ToolUi.primaryButton(I18n.message("json.format"), () -> FormatOperations.formatJson(editor, status)));
        bar.add(ToolUi.button(I18n.message("json.compress"), () -> FormatOperations.compressJson(editor, status)));
        bar.add(ToolUi.button(I18n.message("common.copy"), this::copy));
        JCheckBox wrap = new JCheckBox(I18n.message("json.wrap"));
        wrap.setFocusPainted(false);
        wrap.addActionListener(e -> editor.setLineWrap(wrap.isSelected()));
        bar.add(wrap);

        add(bar, BorderLayout.NORTH);
        add(new RTextScrollPane(editor), BorderLayout.CENTER);
        status.setForeground(JBUI.CurrentTheme.Label.disabledForeground());
        add(status, BorderLayout.SOUTH);
    }

    @Override
    public String title() {
        return "JSON";
    }

    @Override
    public JComponent component() {
        return this;
    }

    private void copy() {
        Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new StringSelection(editor.getText()), null);
        status.setText(I18n.message("json.copied"));
    }

    private void applyTheme() {
        if (!com.damoguyansi.all.format.util.ColorUtil.isDarcula()) {
            return;
        }
        try {
            Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/dark.xml")).apply(editor);
        } catch (Exception ignored) {
        }
    }
}
