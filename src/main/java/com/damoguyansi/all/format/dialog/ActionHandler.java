package com.damoguyansi.all.format.dialog;

import com.damoguyansi.all.format.cache.CacheName;
import com.damoguyansi.all.format.constant.Constants;
import com.damoguyansi.all.format.event.TextPanelMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class ActionHandler {
    private final TranslateDialog dialog;
    private TextPanelMouseListener textPanelMouseListener;

    public ActionHandler(TranslateDialog dialog) {
        this.dialog = dialog;
    }

    public void registerListeners() {
        registerFormatButtonListener();
        registerUtilityButtonListener();
        registerTabChangeListener();
        registerCheckBoxListeners();
        registerUrlEncodingListeners();
        registerSponsorLabelListener();
        registerTextPanelListeners();
        registerWindowListeners();
    }

    private void registerFormatButtonListener() {
        dialog.getFormatButton().addActionListener(e -> {
            String selectedTab = dialog.getTabbedPane().getTitleAt(
                    dialog.getTabbedPane().getSelectedIndex()).trim();
            switch (selectedTab) {
                case Constants.JSON:
                    FormatOperations.formatJson(dialog.getJsonText(), dialog.getStatusLabel());
                    break;
                case Constants.XML:
                    FormatOperations.formatXml(dialog.getXmlText(), dialog.getStatusLabel());
                    break;
                case Constants.HTML:
                    FormatOperations.formatHtml(dialog.getHtmlText(), dialog.getStatusLabel());
                    break;
                case Constants.SQL:
                    FormatOperations.formatSql(dialog.getSqlText(), dialog.getStatusLabel());
                    break;
                case Constants.QRCODE:
                    UtilityOperations.createQrCode(dialog.getQrCodeText(), dialog.getStatusLabel());
                    break;
                case Constants.BASE64:
                    UtilityOperations.encodeBase64(dialog.getBase64Text(), dialog.getStatusLabel());
                    break;
                case Constants.ENCODE:
                    UtilityOperations.encodeUnicode(dialog.getEncodeText(), dialog.getStatusLabel());
                    break;
                case Constants.HEX_CONVERT:
                    dialog.getHexConvertPanel().setValue();
                    break;
                case Constants.TRANSLATE:
                    UtilityOperations.translate(dialog.getTranslateInput(), dialog.getTranslateOutput());
                    break;
            }
        });
    }

    private void registerUtilityButtonListener() {
        dialog.getUtilityButton().addActionListener(e -> {
            String selectedTab = dialog.getTabbedPane().getTitleAt(
                    dialog.getTabbedPane().getSelectedIndex()).trim();
            switch (selectedTab) {
                case Constants.JSON:
                    FormatOperations.compressJson(dialog.getJsonText(), dialog.getStatusLabel());
                    break;
                case Constants.BASE64:
                    UtilityOperations.decodeBase64(dialog.getBase64Text(), dialog.getStatusLabel());
                    break;
                case Constants.ENCODE:
                    UtilityOperations.decodeUnicode(dialog.getEncodeText(), dialog.getStatusLabel());
                    break;
                case Constants.QRCODE:
                    UtilityOperations.decodeQrCode(dialog.getQrCodeText(), dialog.getStatusLabel());
                    break;
            }
        });
    }

    private void registerTabChangeListener() {
        dialog.getTabbedPane().addChangeListener(e -> {
            JTabbedPane pane = (JTabbedPane) e.getSource();
            String selectedTab = dialog.getTabbedPane().getTitleAt(pane.getSelectedIndex()).trim();

            dialog.getUtilityButton().setVisible(false);
            dialog.getWrapLinesCheckBox().setVisible(true);
            dialog.getStatusLabel().setForeground(Color.BLACK);
            dialog.getStatusLabel().setText("\u70b9\u51fb\u6309\u94ae\u8fdb\u884c\u683c\u5f0f\u5316");
            dialog.getFormatButton().setText("\u683c\u5f0f\u5316");
            dialog.getUrlEncodeButton().setVisible(false);
            dialog.getUrlDecodeButton().setVisible(false);
            dialog.getMd5Button().setVisible(false);

            switch (selectedTab) {
                case Constants.JSON:
                    dialog.getUtilityButton().setText("\u538b\u7f29");
                    dialog.getUtilityButton().setVisible(true);
                    break;
                case Constants.BASE64:
                    dialog.getBase64Text().requestFocus();
                    dialog.getUtilityButton().setText("\u89e3\u5bc6");
                    dialog.getUtilityButton().setVisible(true);
                    dialog.getFormatButton().setText("\u52a0\u5bc6");
                    break;
                case Constants.QRCODE:
                    dialog.getFormatButton().setText("\u751f\u6210");
                    dialog.getUtilityButton().setText("\u89e3\u6790");
                    dialog.getUtilityButton().setVisible(true);
                    dialog.getQrCodeText().requestFocus();
                    break;
                case Constants.SQL:
                    dialog.getFormatButton().setText("\u7f8e\u5316");
                    dialog.getSqlText().requestFocus();
                    break;
                case Constants.ENCODE:
                    dialog.getFormatButton().setText("\u4e2d\u8f6cU");
                    dialog.getUtilityButton().setText("U\u8f6c\u4e2d");
                    dialog.getUtilityButton().setVisible(true);
                    dialog.getUrlEncodeButton().setVisible(true);
                    dialog.getUrlDecodeButton().setVisible(true);
                    dialog.getMd5Button().setVisible(true);
                    dialog.getEncodeText().requestFocus();
                    break;
                case Constants.TRANSLATE:
                    dialog.getFormatButton().setText("\u7ffb\u8bd1");
                    dialog.getUtilityButton().setVisible(false);
                    dialog.getWrapLinesCheckBox().setVisible(false);
                    dialog.getTranslateInput().requestFocus();
                    dialog.getStatusLabel().setForeground(Color.RED);
                    dialog.getStatusLabel().setText("选中单词Ctrl+Alt+U 翻译!");
                    break;
                case Constants.HEX_CONVERT:
                    dialog.getHexConvertPanel().setFocus();
                    dialog.getWrapLinesCheckBox().setVisible(false);
                    dialog.getFormatButton().setText("\u8f6c\u6362");
                    break;
            }
        });
    }

    private void registerCheckBoxListeners() {
        dialog.getAlwaysOnTopCheckBox().addChangeListener(e ->
                dialog.setAlwaysOnTop(dialog.getAlwaysOnTopCheckBox().isSelected()));

        dialog.getWrapLinesCheckBox().addChangeListener(e -> {
            boolean wrap = dialog.getWrapLinesCheckBox().isSelected();
            dialog.getJsonText().setLineWrap(wrap);
            dialog.getXmlText().setLineWrap(wrap);
            dialog.getHtmlText().setLineWrap(wrap);
            dialog.getSqlText().setLineWrap(wrap);
            dialog.getBase64Text().setLineWrap(wrap);
            dialog.getEncodeText().setLineWrap(wrap);
        });
    }

    private void registerUrlEncodingListeners() {
        dialog.getUrlEncodeButton().addActionListener(e ->
                UtilityOperations.encodeUrl(dialog.getEncodeText(), dialog.getStatusLabel()));

        dialog.getUrlDecodeButton().addActionListener(e ->
                UtilityOperations.decodeUrl(dialog.getEncodeText(), dialog.getStatusLabel()));

        dialog.getMd5Button().addActionListener(e ->
                UtilityOperations.generateMd5(dialog.getEncodeText(), dialog.getStatusLabel()));
    }

    private void registerSponsorLabelListener() {
        dialog.getSponsorLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(() -> {
                    try {
                        Desktop.getDesktop().browse(new URI(dialog.getCodeGithubUrl()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        });
    }

    private void registerTextPanelListeners() {
        textPanelMouseListener = new TextPanelMouseListener(dialog.getTabbedPane());
        dialog.getQrCodeText().addMouseListener(textPanelMouseListener);
        dialog.getBase64Text().addMouseListener(textPanelMouseListener);
        dialog.getEncodeText().addMouseListener(textPanelMouseListener);
        dialog.getTranslateInput().addMouseListener(textPanelMouseListener);
    }

    private void registerWindowListeners() {
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.cache.writeByName(CacheName.NEW_LINE,
                        Boolean.toString(dialog.getWrapLinesCheckBox().isSelected()));
                dialog.cache.writeByName(CacheName.ON_TOP,
                        Boolean.toString(dialog.isAlwaysOnTop()));
                dialog.cache.close();
                dialog.setVisible(false);
                dialog.dispose();
            }
        });

        dialog.getCenterPanel().registerKeyboardAction(
                e -> {
                    dialog.cache.writeByName(CacheName.NEW_LINE,
                            Boolean.toString(dialog.getWrapLinesCheckBox().isSelected()));
                    dialog.cache.writeByName(CacheName.ON_TOP,
                            Boolean.toString(dialog.isAlwaysOnTop()));
                    dialog.cache.close();
                    dialog.setVisible(false);
                    dialog.dispose();
                },
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }
}