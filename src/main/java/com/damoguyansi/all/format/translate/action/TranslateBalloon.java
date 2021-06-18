package com.damoguyansi.all.format.translate.action;

import com.damoguyansi.all.format.translate.ui.modules.ColorService;
import com.damoguyansi.all.format.translate.ui.modules.IdeaColorService;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;

class TranslateBalloon {
    static {
        ColorService.install(new IdeaColorService());
    }

    private GoogleTranslateResult result;

    private JPanel jPanel;

    static JEditorPane origEditorPane = new JEditorPane();

    static JEditorPane transEditorPane = new JEditorPane();

    private int origLength;

    private int transLength;

    private int height;

    private int width;

    JPanel getjPanel() {
        return jPanel;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    TranslateBalloon(GoogleTranslateResult result) {
        this.result = result;
        init();
    }

    private void init() {
        jPanel = new JPanel(new BorderLayout());
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        StringBuilder origBuilder = new StringBuilder("<body style='font-size:14px;font-weight: bold;color: #CAA923;'>");
        StringBuilder transBuilder = new StringBuilder("<body style='font-size:14px;font-weight: bold;color: #CAA923;'>");
        if (result.getSentences().size() == 1 || result.getSentences().size() == 2) {
            GoogleTranslateResult.SentencesBean sentencesBean = result.getSentences().get(0);
            origBuilder.append("<div style='color:red;font-size:19px;'>");
            origBuilder.append(result.getSentences().get(0).getOrig());
            origBuilder.append("</div>");
            transBuilder.append(result.getSentences().get(0).getTrans()).append("<br>");
            if (!sentencesBean.getOrig().equals(sentencesBean.getTrans()) && result.getDict() != null) {
                for (int i = 0, len = result.getDict().size(); i < len; i++) {
                    transBuilder.append(result.getDict().get(i));
                }
            }
        } else {
            for (GoogleTranslateResult.SentencesBean sentence : result.getSentences()) {
                if (sentence.getTrans() != null) {
                    origBuilder.append(sentence.getOrig());
                    transBuilder.append(sentence.getTrans());
                }
            }
        }
        origBuilder.append("</body>");
        transBuilder.append("</body>");
        origLength = origBuilder.length();
        transLength = transBuilder.length();
        createPanel("原文：", origBuilder, BorderLayout.NORTH, origEditorPane);
        createPanel("翻译结果：", transBuilder, BorderLayout.CENTER, transEditorPane);
    }

    private void createPanel(String text, StringBuilder builder, String position, JEditorPane editorPane) {
        System.out.println(builder.toString());
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text);
        label.setForeground(new Color(16, 187, 100));
        label.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        panel.add(label, BorderLayout.NORTH);
        editorPane.setContentType("text/html");
        editorPane.setText(builder.toString());
        editorPane.setEditable(false);
        editorPane.setBackground(ColorService.forCurrentTheme(ColorService.Background));
        editorPane.setSelectionStart(0);
        editorPane.setSelectionEnd(0);
        editorPane.getSelectedText();
        JBScrollPane scrollPane = new JBScrollPane(editorPane);
        scrollPane.setBorder(null);
        scrollPane.setMaximumSize(new Dimension(520, 200));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(builder.length(), panel);
        jPanel.add(panel, position);
    }

    private void setPreferredSize(int len, JPanel panel) {
        boolean flag = len * 16 > 540;
        int width = flag ? 520 : (len * 16 + 80);
        int height = flag ? Math.min(((len * 16 / 520 + 2) * 16), 200) : 32;
        this.height += height;
        this.width = Math.max(this.width, width);
        panel.setPreferredSize(new Dimension(width, height));
    }

    public static void main(String[] args) {
        TranslateBalloon translateBalloon = new TranslateBalloon(null);
//        TranslateBalloon translateBalloon = new TranslateBalloon();
        JFrame jFrame = new JFrame();
        jFrame.add(translateBalloon.jPanel);
        jFrame.setSize(520, 400);
        jFrame.setVisible(true);
    }
}
