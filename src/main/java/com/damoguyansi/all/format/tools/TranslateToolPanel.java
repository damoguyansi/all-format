package com.damoguyansi.all.format.tools;

import com.damoguyansi.all.format.i18n.I18n;
import com.damoguyansi.all.format.settings.AppSettings;
import com.damoguyansi.all.format.translate.TranslationService;
import com.damoguyansi.all.format.translate.bean.TransResult;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * 翻译工具（重构版）：上方输入，下方富文本结果（含音标与词典释义）。
 * 自动识别语种，输入停顿后自动翻译（延迟可在设置页配置）。
 *
 * @author damoguyansi
 */
public class TranslateToolPanel extends JPanel implements ToolPanel {

    private final JBTextArea source = new JBTextArea();
    private final JEditorPane resultPane = new JEditorPane();
    private final Timer timer;

    public TranslateToolPanel() {
        super(new BorderLayout(0, 6));
        setBorder(JBUI.Borders.empty(8));

        JPanel bar = ToolUi.toolbar();
        JBLabel hint = new JBLabel(I18n.message("translate.hint"));
        hint.setForeground(JBUI.CurrentTheme.Label.disabledForeground());
        bar.add(ToolUi.primaryButton(I18n.message("translate.action"), this::translate));
        bar.add(ToolUi.button(I18n.message("common.clear"), this::clear));
        bar.add(hint);
        add(bar, BorderLayout.NORTH);

        source.setLineWrap(true);
        source.setRows(3);
        source.setBorder(JBUI.Borders.empty(6));

        resultPane.setContentType("text/html");
        resultPane.setEditable(false);
        resultPane.setBorder(JBUI.Borders.empty(6));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JBScrollPane(source), new JBScrollPane(resultPane));
        split.setResizeWeight(0.35);
        split.setBorder(JBUI.Borders.empty());
        add(split, BorderLayout.CENTER);

        timer = new Timer(700, e -> translate());
        timer.setRepeats(false);
        source.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { schedule(); }
            public void removeUpdate(DocumentEvent e) { schedule(); }
            public void changedUpdate(DocumentEvent e) { schedule(); }
        });
    }

    @Override
    public String title() {
        return "Translate";
    }

    @Override
    public JComponent component() {
        return this;
    }

    private void clear() {
        source.setText("");
        resultPane.setText("");
    }

    private void schedule() {
        int delay = AppSettings.getInstance().getAutoTranslateDelay();
        if (delay <= 0) {
            timer.stop();
            return;
        }
        if (source.getText().trim().isEmpty()) {
            timer.stop();
            resultPane.setText("");
        } else {
            timer.setInitialDelay(delay);
            timer.restart();
        }
    }

    private void translate() {
        String text = source.getText().trim();
        if (text.isEmpty()) {
            return;
        }
        setHtml(I18n.message("translate.loading"), true);
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            String html;
            boolean muted = false;
            try {
                TransResult result = TranslationService.translate(text);
                html = result.toString();
            } catch (Exception e) {
                html = I18n.message("translate.failed", e.getMessage());
                muted = true;
            }
            String finalHtml = html;
            boolean finalMuted = muted;
            ApplicationManager.getApplication().invokeLater(() -> setHtml(finalHtml, finalMuted));
        });
    }

    private void setHtml(String body, boolean muted) {
        String color = muted ? "color:#888;" : "";
        resultPane.setText("<html><body style='font-family:sans-serif;margin:4px;" + color + "'>"
                + body + "</body></html>");
        resultPane.setCaretPosition(0);
    }
}
