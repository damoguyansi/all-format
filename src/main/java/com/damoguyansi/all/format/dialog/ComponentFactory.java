package com.damoguyansi.all.format.dialog;

import com.damoguyansi.all.format.cache.CacheName;
import com.damoguyansi.all.format.constant.Constants;
import com.damoguyansi.all.format.i18n.I18n;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.IOException;

public class ComponentFactory {
    private static final com.intellij.openapi.diagnostic.Logger LOG =
            com.intellij.openapi.diagnostic.Logger.getInstance(ComponentFactory.class);

    private final TranslateDialog dialog;
    private final boolean isDarkTheme;

    public ComponentFactory(TranslateDialog dialog, boolean isDarkTheme) {
        this.dialog = dialog;
        this.isDarkTheme = isDarkTheme;
    }

    public void initializeComponents() {
        setupTextComponents();
        configureButtons();
        createSyntaxTextAreas();
        removeRetiredTabs();
        com.damoguyansi.all.format.tools.ToolRegistry.install(dialog.getTabbedPane());
        applyTheme();
    }

    /** 移除已下线或被重构的标签页（HTML/SQL/HexConvert 以及表单内的旧 QRCode/Translate）。 */
    private void removeRetiredTabs() {
        JTabbedPane pane = dialog.getTabbedPane();
        java.util.Set<String> retired = new java.util.HashSet<>(java.util.Arrays.asList(
                Constants.JSON, Constants.HTML, Constants.SQL, Constants.HEX_CONVERT,
                Constants.QRCODE, Constants.BASE64, Constants.ENCODE, Constants.TRANSLATE));
        for (int i = pane.getTabCount() - 1; i >= 0; i--) {
            if (retired.contains(pane.getTitleAt(i).trim())) {
                pane.removeTabAt(i);
            }
        }
    }

    public void loadCachedParameters() {
        Boolean onTopParam = dialog.cache.readAsBoolean(CacheName.ON_TOP);
        if (onTopParam == null || onTopParam) {
            dialog.getAlwaysOnTopCheckBox().setSelected(true);
            dialog.setAlwaysOnTop(true);
        } else {
            dialog.getAlwaysOnTopCheckBox().setSelected(false);
            dialog.setAlwaysOnTop(false);
        }

        Boolean wrapLinesParam = dialog.cache.readAsBoolean(CacheName.NEW_LINE);
        if (wrapLinesParam != null && wrapLinesParam) {
            dialog.getWrapLinesCheckBox().setSelected(true);
        } else {
            dialog.getWrapLinesCheckBox().setSelected(false);
        }
    }

    private void setupTextComponents() {
        dialog.getEncodeText().setOpaque(false);
        dialog.getBase64Text().setOpaque(false);
        dialog.getQrCodeText().setOpaque(false);

        dialog.getTranslateInput().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.getTranslateInput().setLineWrap(true);
        dialog.getTranslateInput().setDocument(new MaxLengthDocument(300));

        dialog.getTranslateOutput().setEditable(false);
        dialog.getTranslateOutput().setLineWrap(true);
        dialog.getTranslateOutput().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.getTranslateOutput().setBackground(dialog.getTranslateOutputPane().getBackground());
    }

    private void configureButtons() {
        // \u6240\u6709\u529f\u80fd\u5df2\u8fc1\u79fb\u5230\u5404\u81ea\u7684\u5de5\u5177\u6807\u7b7e\u9875\uff0c\u65e7\u7684\u5e95\u90e8\u5171\u4eab\u63a7\u4ef6\u4e0d\u518d\u4f7f\u7528\uff0c\u7edf\u4e00\u9690\u85cf
        dialog.getFormatButton().setVisible(false);
        dialog.getUtilityButton().setVisible(false);
        dialog.getWrapLinesCheckBox().setVisible(false);
        dialog.getUrlEncodeButton().setVisible(false);
        dialog.getUrlDecodeButton().setVisible(false);
        dialog.getMd5Button().setVisible(false);
        dialog.getAlwaysOnTopCheckBox().setVisible(false);
        dialog.getSponsorLabel().setVisible(false);
        dialog.getStatusLabel().setText("");
    }

    private void createSyntaxTextAreas() {
        RSyntaxTextArea jsonText = createArea(Constants.JSON);
        RTextScrollPane jsonSp = new RTextScrollPane(jsonText);
        jsonSp.setBorder(new EmptyBorder(0, 0, 0, 0));
        dialog.getJsonPanel().add(jsonSp);
        dialog.setJsonText(jsonText);

        RSyntaxTextArea xmlText = createArea(Constants.XML);
        RTextScrollPane xmlSp = new RTextScrollPane(xmlText);
        xmlSp.setBorder(new EmptyBorder(0, 0, 0, 0));
        dialog.setXmlText(xmlText);

        RSyntaxTextArea htmlText = createArea(Constants.HTML);
        RTextScrollPane htmlSp = new RTextScrollPane(htmlText);
        htmlSp.setBorder(new EmptyBorder(0, 0, 0, 0));
        dialog.getHtmlPanel().add(htmlSp);
        dialog.setHtmlText(htmlText);

        RSyntaxTextArea sqlText = createArea(Constants.SQL);
        RTextScrollPane sqlSp = new RTextScrollPane(sqlText);
        sqlSp.setBorder(new EmptyBorder(0, 0, 0, 0));
        dialog.getSqlPanel().add(sqlSp);
        dialog.setSqlText(sqlText);
    }

    private void applyTheme() {
        // 跟随 IDE 主题的扁平化背景，而非硬编码颜色
        Color paneBg = JBUI.CurrentTheme.CustomFrameDecorations.paneBackground();
        dialog.getContentPane().setBackground(paneBg);
        dialog.getTabbedPane().setForeground(JBColor.foreground());

        if (isDarkTheme) {
            applyDarkTheme(dialog.getJsonText());
            applyDarkTheme(dialog.getXmlText());
            applyDarkTheme(dialog.getHtmlText());
            applyDarkTheme(dialog.getSqlText());
        } else {
            configureLightTheme(dialog.getJsonText());
            configureLightTheme(dialog.getXmlText());
            configureLightTheme(dialog.getHtmlText());
            configureLightTheme(dialog.getSqlText());
        }
    }

    private RSyntaxTextArea createArea(String type) {
        RSyntaxTextArea area = new RSyntaxTextArea();
        area.setDocument(new MaxLengthDocument(5000000));
        
        switch (type) {
            case Constants.JSON:
                area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
                break;
            case Constants.XML:
                area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
                break;
            case Constants.HTML:
                area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
                break;
            case Constants.SQL:
                area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
                break;
        }
        
        area.setCodeFoldingEnabled(true);
        area.setAntiAliasingEnabled(true);
        area.setAutoscrolls(true);
        return area;
    }

    private void applyDarkTheme(RSyntaxTextArea area) {
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                "/org/fife/ui/rsyntaxtextarea/themes/dark.xml"));
            theme.apply(area);
        } catch (IOException e) {
            LOG.warn("apply dark theme failed", e);
        }
    }

    private void configureLightTheme(RSyntaxTextArea area) {
        SyntaxScheme scheme = area.getSyntaxScheme();
        scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.BLUE;
        scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = new Color(164, 0, 0);
        scheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground = new Color(164, 0, 0);
        scheme.getStyle(Token.LITERAL_BOOLEAN).foreground = Color.RED;
        scheme.getStyle(Token.OPERATOR).foreground = Color.BLACK;
        area.revalidate();
    }

    public class MaxLengthDocument extends RSyntaxDocument {
        private final int maxChars;

        public MaxLengthDocument(int max) {
            super(SyntaxConstants.SYNTAX_STYLE_NONE);
            this.maxChars = max;
        }

        @Override
        public void insertString(int offset, String s, AttributeSet a) throws BadLocationException {
            try {
                if (getLength() + s.length() > maxChars) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(dialog,
                        I18n.message("component.contentTooLong", maxChars),
                        I18n.message("component.warning"),
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                super.insertString(offset, s, a);
            } catch (Exception e) {
                LOG.warn(e);
            }
        }
    }
}
