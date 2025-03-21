package com.damoguyansi.all.format.dialog;

import com.damoguyansi.all.format.cache.CacheName;
import com.damoguyansi.all.format.constant.Constants;
import com.damoguyansi.all.format.util.ClipboardUtil;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.IOException;

public class ComponentFactory {
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
        applyTheme();
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
        dialog.getUtilityButton().setText("\u538b\u7f29"); // Compress
        dialog.getUtilityButton().setVisible(true);
        
        dialog.getSponsorLabel().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        dialog.getUrlEncodeButton().setVisible(false);
        dialog.getUrlDecodeButton().setVisible(false);
        dialog.getMd5Button().setVisible(false);
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
        dialog.getXmlPanel().add(xmlSp);
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
        if (isDarkTheme) {
            dialog.getTabbedPane().setForeground(new Color(213, 212, 212));
            Color backgroundColor = Color.DARK_GRAY;
            dialog.getContentPane().setBackground(backgroundColor);
            
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
            e.printStackTrace();
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
                        "内容过长，最大" + maxChars + "个字符!", 
                        "提示", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                super.insertString(offset, s, a);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}