package com.damoguyansi.all.format.dialog;

import com.damoguyansi.all.format.component.ImageLabel;
import com.damoguyansi.all.format.translate.TransApiFactory;
import com.damoguyansi.all.format.translate.bean.ApiCode;
import com.damoguyansi.all.format.translate.bean.TransResult;
import com.damoguyansi.all.format.util.*;
import com.google.common.io.BaseEncoding;
import cn.hutool.core.util.URLUtil;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.image.BufferedImage;
import java.util.Locale;
import java.util.regex.Matcher;

public class UtilityOperations {

    public static void createQrCode(JTextPane qrCodeText, JLabel statusLabel) {
        String text = getTrimmedText(qrCodeText);
        if (text == null) return;

        try {
            qrCodeText.setText(text + "\r\n");
            qrCodeText.setCaretPosition(qrCodeText.getStyledDocument().getLength());
            BufferedImage bufferedImage = QrCodeCreateUtil.createQrCode(text, 250);
            ImageIcon imageIcon = new ImageIcon(bufferedImage);
            ImageLabel label = new ImageLabel(qrCodeText, imageIcon);
            qrCodeText.insertComponent(label);
            statusLabel.setText("qrcode create!");
        } catch (Exception e) {
            String error = "qrcode create exception [" + e.getMessage() + "]";
            statusLabel.setText(error);
            statusLabel.setToolTipText(error);
        }
    }

    public static void decodeQrCode(JTextPane qrCodeText, JLabel statusLabel) {
        try {
            StringBuilder results = new StringBuilder();
            StyledDocument doc = (StyledDocument) qrCodeText.getDocument();
            for (int i = 0; i < doc.getLength(); i++) {
                Element elem = doc.getCharacterElement(i);
                AttributeSet as = elem.getAttributes();
                if (as.containsAttribute(javax.swing.text.AbstractDocument.ElementNameAttribute, 
                    StyleConstants.ComponentElementName)) {
                    if (StyleConstants.getComponent(as) instanceof JLabel) {
                        ImageLabel myLabel = (ImageLabel) StyleConstants.getComponent(as);
                        ImageIcon imageIcon = myLabel.getImageIcon();
                        results.append(QrCodeCreateUtil.decode(imageIcon.getImage())).append("\r\n");
                    }
                }
            }
            
            if (results.length() == 0) {
                statusLabel.setText("未解析到图片内容");
                statusLabel.setToolTipText(statusLabel.getText());
            } else {
                qrCodeText.setCaretPosition(qrCodeText.getStyledDocument().getLength());
                qrCodeText.setText(results.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("未解析到图片内容");
            statusLabel.setToolTipText(statusLabel.getText());
        }
    }

    public static void encodeBase64(JTextArea base64Text, JLabel statusLabel) {
        String text = base64Text.getText();
        if (isEmpty(text)) return;

        try {
            String encoded = BaseEncoding.base64().encode(text.getBytes());
            base64Text.setText(encoded);
            statusLabel.setText("base64 encode!");
            statusLabel.setToolTipText("base64 encode!");
        } catch (Exception e) {
            String error = "base64 encode exception [" + e.getMessage() + "]";
            statusLabel.setText(error);
            statusLabel.setToolTipText(error);
        }
    }

    public static void decodeBase64(JTextArea base64Text, JLabel statusLabel) {
        String text = base64Text.getText();
        if (isEmpty(text)) return;

        try {
            String decoded = new String(BaseEncoding.base64().decode(text));
            base64Text.setText(decoded);
            statusLabel.setText("base64 decode!");
            statusLabel.setToolTipText("base64 decode!");
        } catch (Exception e) {
            String error = "base64 decode exception [" + e.getMessage() + "]";
            statusLabel.setText(error);
            statusLabel.setToolTipText(error);
        }
    }

    public static void encodeUnicode(JTextArea encodeText, JLabel statusLabel) {
        String text = encodeText.getText();
        if (isEmpty(text)) return;

        String result = UnicodeUtil.unicodeEncode(text);
        if (result == null) {
            statusLabel.setText("unicode encode error!");
            return;
        }
        encodeText.setText(result);
        statusLabel.setText("unicode encode!");
        statusLabel.setToolTipText("unicode encode!");
    }

    public static void decodeUnicode(JTextArea encodeText, JLabel statusLabel) {
        String text = encodeText.getText();
        if (isEmpty(text)) return;

        String result = UnicodeUtil.unicodeDecode(text);
        if (result == null) {
            statusLabel.setText("unicode decode error!");
            return;
        }
        encodeText.setText(result);
        statusLabel.setText("unicode decode!");
        statusLabel.setToolTipText("unicode decode!");
    }

    public static void encodeUrl(JTextArea encodeText, JLabel statusLabel) {
        String text = encodeText.getText();
        if (isEmpty(text)) return;

        encodeText.setText(URLUtil.encode(text));
        statusLabel.setText("url encode success!");
    }

    public static void decodeUrl(JTextArea encodeText, JLabel statusLabel) {
        String text = encodeText.getText();
        if (isEmpty(text)) return;

        encodeText.setText(URLUtil.decode(text));
        statusLabel.setText("url decode success!");
    }

    public static void generateMd5(JTextArea encodeText, JLabel statusLabel) {
        String text = encodeText.getText();
        if (isEmpty(text)) return;

        try {
            String md5 = MD5Util.md5(text).toUpperCase(Locale.ROOT);
            encodeText.setText(md5);
            statusLabel.setText("md5 success!");
        } catch (Throwable t) {
            String error = "md5 error [" + t.getMessage() + "]";
            statusLabel.setText(error);
            statusLabel.setToolTipText(error);
        }
    }

    public static void translate(JTextArea inputText, JTextArea outputText) {
        String text = getTrimmedText(inputText);
        if (text == null) return;

        Matcher matcher = TranslateUtil.p.matcher(text);
        String translateType = matcher.find() ? TranslateUtil.ZH_CN_TO_EN : TranslateUtil.EN_TO_ZH_CN;
        
        try {
            TransResult result = TransApiFactory.createApi(ApiCode.BAIDU).translate(text, translateType);
            outputText.setText(result == null ? "未知翻译" : result.getSentences().get(0).getTrans());
        } catch (Exception e) {
            outputText.setText("未知");
        }
    }

    private static String getTrimmedText(JTextComponent textComponent) {
        String text = textComponent.getText();
        if (isEmpty(text)) return null;
        return text.trim();
    }

    private static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}