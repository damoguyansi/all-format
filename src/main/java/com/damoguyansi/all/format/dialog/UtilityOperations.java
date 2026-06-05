package com.damoguyansi.all.format.dialog;

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

    private static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}