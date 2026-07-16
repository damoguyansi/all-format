package com.damoguyansi.all.format.dialog;

import com.damoguyansi.all.format.i18n.I18n;
import com.damoguyansi.all.format.util.*;
import com.google.common.io.BaseEncoding;
import cn.hutool.core.util.URLUtil;

import javax.swing.*;
import javax.swing.text.*;
import java.util.Locale;

public class UtilityOperations {

    public static void encodeBase64(JTextArea base64Text, JLabel statusLabel) {
        String text = base64Text.getText();
        if (isEmpty(text)) return;

        try {
            String encoded = BaseEncoding.base64().encode(text.getBytes());
            base64Text.setText(encoded);
            String success = I18n.message("status.encodeSuccess", "Base64");
            statusLabel.setText(success);
            statusLabel.setToolTipText(success);
        } catch (Exception e) {
            String error = I18n.message("status.operationFailed", "Base64", e.getMessage());
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
            String success = I18n.message("status.decodeSuccess", "Base64");
            statusLabel.setText(success);
            statusLabel.setToolTipText(success);
        } catch (Exception e) {
            String error = I18n.message("status.operationFailed", "Base64", e.getMessage());
            statusLabel.setText(error);
            statusLabel.setToolTipText(error);
        }
    }

    public static void encodeUnicode(JTextArea encodeText, JLabel statusLabel) {
        String text = encodeText.getText();
        if (isEmpty(text)) return;

        String result = UnicodeUtil.unicodeEncode(text);
        if (result == null) {
            statusLabel.setText(I18n.message("status.operationFailed", "Unicode", ""));
            return;
        }
        encodeText.setText(result);
        String success = I18n.message("status.encodeSuccess", "Unicode");
        statusLabel.setText(success);
        statusLabel.setToolTipText(success);
    }

    public static void decodeUnicode(JTextArea encodeText, JLabel statusLabel) {
        String text = encodeText.getText();
        if (isEmpty(text)) return;

        String result = UnicodeUtil.unicodeDecode(text);
        if (result == null) {
            statusLabel.setText(I18n.message("status.operationFailed", "Unicode", ""));
            return;
        }
        encodeText.setText(result);
        String success = I18n.message("status.decodeSuccess", "Unicode");
        statusLabel.setText(success);
        statusLabel.setToolTipText(success);
    }

    public static void encodeUrl(JTextArea encodeText, JLabel statusLabel) {
        String text = encodeText.getText();
        if (isEmpty(text)) return;

        encodeText.setText(URLUtil.encode(text));
        statusLabel.setText(I18n.message("status.encodeSuccess", "URL"));
    }

    public static void decodeUrl(JTextArea encodeText, JLabel statusLabel) {
        String text = encodeText.getText();
        if (isEmpty(text)) return;

        encodeText.setText(URLUtil.decode(text));
        statusLabel.setText(I18n.message("status.decodeSuccess", "URL"));
    }

    public static void generateMd5(JTextArea encodeText, JLabel statusLabel) {
        String text = encodeText.getText();
        if (isEmpty(text)) return;

        try {
            String md5 = MD5Util.md5(text).toUpperCase(Locale.ROOT);
            encodeText.setText(md5);
            statusLabel.setText(I18n.message("status.formatSuccess", "MD5"));
        } catch (Throwable t) {
            String error = I18n.message("status.operationFailed", "MD5", t.getMessage());
            statusLabel.setText(error);
            statusLabel.setToolTipText(error);
        }
    }

    private static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
