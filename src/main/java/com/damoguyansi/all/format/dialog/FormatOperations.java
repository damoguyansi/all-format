package com.damoguyansi.all.format.dialog;

import com.damoguyansi.all.format.i18n.I18n;
import com.damoguyansi.all.format.util.FormatUtil;
import com.damoguyansi.all.format.util.HtmlFormat;
import com.damoguyansi.all.format.util.MapFormat;
import com.damoguyansi.all.format.util.SqlFormat;
import com.damoguyansi.all.format.util.XmlFormat;
import cn.hutool.json.JSONUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;

public class FormatOperations {

    public static void formatJson(RSyntaxTextArea jsonText, JLabel statusLabel) {
        String text = getTrimmedText(jsonText);
        if (text == null) return;

        try {
            String formatted = FormatUtil.format(text);
            jsonText.setText(formatted);
            statusLabel.setText(I18n.message("status.formatSuccess", "JSON"));
        } catch (Exception e) {
            String formatted = MapFormat.format(text);
            jsonText.setText(formatted);
            statusLabel.setText(I18n.message("status.formatSuccess", "Map"));
        }
        jsonText.setCaretPosition(0);
    }

    public static void compressJson(RSyntaxTextArea jsonText, JLabel statusLabel) {
        formatJson(jsonText, statusLabel);
        String text = jsonText.getText();
        if (isEmpty(text)) return;
        
        jsonText.setText(JSONUtil.parse(text).toString());
        statusLabel.setText(I18n.message("status.compressSuccess", "JSON"));
    }

    public static void formatXml(RSyntaxTextArea xmlText, JLabel statusLabel) {
        String text = getTrimmedText(xmlText);
        if (text == null) return;

        try {
            String formatted = XmlFormat.format(text);
            xmlText.setText(formatted);
            statusLabel.setText(I18n.message("status.formatSuccess", "XML"));
        } catch (Throwable e) {
            String error = I18n.message("status.operationFailed", "XML", e.getMessage());
            statusLabel.setText(error);
            statusLabel.setToolTipText(error);
        }
    }

    public static void formatHtml(RSyntaxTextArea htmlText, JLabel statusLabel) {
        String text = getTrimmedText(htmlText);
        if (text == null) return;

        try {
            String formatted = HtmlFormat.format(text);
            htmlText.setText(formatted);
            statusLabel.setText(I18n.message("status.formatSuccess", "HTML"));
        } catch (Throwable e) {
            String error = I18n.message("status.operationFailed", "HTML", e.getMessage());
            statusLabel.setText(error);
            statusLabel.setToolTipText(error);
        }
    }

    public static void formatSql(RSyntaxTextArea sqlText, JLabel statusLabel) {
        String text = sqlText.getText();
        if (isEmpty(text)) return;

        String formatted = SqlFormat.format(text);
        if (formatted == null) {
            statusLabel.setText(I18n.message("status.operationFailed", "SQL", ""));
            return;
        }
        sqlText.setText(formatted);
        String success = I18n.message("status.formatSuccess", "SQL");
        statusLabel.setText(success);
        statusLabel.setToolTipText(success);
    }

    private static String getTrimmedText(RSyntaxTextArea textArea) {
        String text = textArea.getText();
        if (isEmpty(text)) return null;
        return text.replaceAll("\t", "");
    }

    private static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
