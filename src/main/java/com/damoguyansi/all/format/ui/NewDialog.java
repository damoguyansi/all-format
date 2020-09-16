package com.damoguyansi.all.format.ui;

import com.damoguyansi.all.format.util.*;
import com.google.common.io.BaseEncoding;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class NewDialog extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel contentPane;
    private JButton exeBtn;
    private JTextArea md5Text;
    private JTextPane qrcodeText;
    private JLabel msgLabel;
    private JTextArea base64Text;
    private JCheckBox topCheckBox;
    private JButton otherBtn;
    private JCheckBox newLineCheckBox;
    private JTextArea unicodeText;

    private JPanel jsPanel;
    private JPanel xmlPanel;
    private JPanel htmlPanel;
    private JPanel sqlPanel;

    private RSyntaxTextArea jsonText;
    private RSyntaxTextArea xmlText;
    private RSyntaxTextArea htmlText;
    private RSyntaxTextArea sqlText;

    private static final String JSON = "JSON";
    private static final String XML = "XML";
    private static final String HTML = "HTML";
    private static final String SQL = "SQL";
    private static final String MD5 = "MD5";
    private static final String QRCODE = "QRCODE";
    private static final String Base64 = "Base64";
    private static final String Unicode = "Unicode";


    public NewDialog() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(exeBtn);

        unicodeText.setOpaque(false);
        base64Text.setOpaque(false);
        md5Text.setOpaque(false);
        otherBtn.setVisible(false);

        initActionListener();

        createRSyntaxTextArea();

        jsonText.setText(ClipboardUtil.getSysClipboardText());

        showDia();
    }

    private void showDia() {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int sWidth = (int) screensize.getWidth();
        int sHeight = (int) screensize.getHeight();
        int w = 600;
        int h = 400;
        int x = (sWidth - w) / 2;
        int y = (sHeight - h) / 2;

        this.pack();
        Rectangle rectangle = new Rectangle(x, y, w, h);
        this.setBounds(rectangle);
        this.setTitle("AllFormat (damoguyansi@163.com)");
        this.setAlwaysOnTop(true);
        this.setVisible(true);

        jsonText.requestFocus();
        jsonText.grabFocus();
    }

    private void initActionListener() {
        exeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tag = tabbedPane1.getTitleAt(tabbedPane1.getSelectedIndex());
                switch (tag.trim()) {
                    case JSON:
                        jsonOK();
                        break;
                    case XML:
                        xmlOK();
                        break;
                    case HTML:
                        htmlOK();
                        break;
                    case MD5:
                        md5OK();
                        break;
                    case QRCODE:
                        qrCodeOK();
                        break;
                    case Base64:
                        encode();
                        break;
                    case SQL:
                        sqlFormat();
                        break;
                    case Unicode:
                        zhToUnicode();
                        break;
                }
            }
        });
        topCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectTop(topCheckBox.isSelected());
            }
        });
        newLineCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newLineCheckBox.isSelected()) {
                    jsonText.setLineWrap(true);
                    xmlText.setLineWrap(true);
                    htmlText.setLineWrap(true);
                    sqlText.setLineWrap(true);
                    md5Text.setLineWrap(true);
                    base64Text.setLineWrap(true);
                    unicodeText.setLineWrap(true);
                } else {
                    jsonText.setLineWrap(false);
                    xmlText.setLineWrap(false);
                    htmlText.setLineWrap(false);
                    sqlText.setLineWrap(false);
                    md5Text.setLineWrap(false);
                    base64Text.setLineWrap(false);
                    unicodeText.setLineWrap(false);
                }
            }
        });
        otherBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tag = tabbedPane1.getTitleAt(tabbedPane1.getSelectedIndex()).trim();
                if (Base64.equalsIgnoreCase(tag)) {
                    decode();
                } else if (Unicode.equalsIgnoreCase(tag)) {
                    unicodeToZh();
                }
            }
        });
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane pane = (JTabbedPane) e.getSource();
                String tag = tabbedPane1.getTitleAt(pane.getSelectedIndex()).trim();
                otherBtn.setVisible(false);
                msgLabel.setText("\u70b9\u51fb\u6309\u94ae\u8fdb\u884c\u683c\u5f0f\u5316");
                if (Base64.equalsIgnoreCase(tag)) {
                    otherBtn.setText("\u89e3\u5bc6");
                    otherBtn.setVisible(true);
                    exeBtn.setText("\u52a0\u5bc6");
                } else if (MD5.equalsIgnoreCase(tag)) {
                    exeBtn.setText("\u7b7e\u540d");
                } else if (QRCODE.equalsIgnoreCase(tag)) {
                    exeBtn.setText("\u751f\u6210");
                } else if (SQL.equalsIgnoreCase(tag)) {
                    exeBtn.setText("\u7f8e\u5316");
                } else if (Unicode.equalsIgnoreCase(tag)) {
                    exeBtn.setText("\u4e2d\u8f6c\u0055");
                    otherBtn.setText("\u0055\u8f6c\u4e2d");
                    otherBtn.setVisible(true);
                } else {
                    exeBtn.setText("\u683c\u5f0f\u5316");
                }
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void selectTop(boolean flag) {
        this.setAlwaysOnTop(flag);
    }

    private void jsonOK() {
        String text = jsonText.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;

        String json = text.replaceAll("\t", "");
        String resStr = null;
        try {
            resStr = JsonFormatTool.format(json);
            msgLabel.setText("json format");
            jsonText.setText(resStr);
        } catch (Throwable e) {
            resStr = MapFormat.format(json);
            msgLabel.setText("map format");
            jsonText.setText(resStr);
        }
    }

    private void xmlOK() {
        String text = xmlText.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;

        String json = text.replaceAll("\t", "");
        try {
            String resStr = XmlFormat.format(json);
            msgLabel.setText("xml format success");
            xmlText.setText(resStr);
        } catch (Throwable e) {
            String eStr = "xml format error [" + e.getMessage() + "]";
            msgLabel.setText(eStr);
            msgLabel.setToolTipText(eStr);
        }
    }

    private void htmlOK() {
        String text = htmlText.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;

        String json = text.replaceAll("\t", "");
        try {
            String resStr = HtmlFormat.format(json);
            msgLabel.setText("html format");
            htmlText.setText(resStr);
        } catch (Throwable e) {
            String eStr = "html format error [" + e.getMessage() + "]";
            msgLabel.setText(eStr);
            msgLabel.setToolTipText(eStr);
        }
    }

    private void md5OK() {
        String text = md5Text.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;
        try {
            String md5Str = MD5Util.encoderByMd5(text);
            msgLabel.setText("md5 success");
            md5Text.setText(md5Str);
        } catch (Throwable t) {
            String eStr = "md5 error [" + t.getMessage() + "]";
            msgLabel.setText(eStr);
            msgLabel.setToolTipText(eStr);
        }
    }

    private void qrCodeOK() {
        String text = qrcodeText.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;

        try {
            qrcodeText.setText(text.trim() + "\r\n");
            qrcodeText.setCaretPosition(qrcodeText.getStyledDocument().getLength());
            qrcodeText.insertIcon(new ImageIcon(QrCodeCreateUtil.createQrCode(text.trim(), 250)));
        } catch (Exception e) {
            String eStr = "create qrCode excpeiont [" + e.getMessage() + "]";
            msgLabel.setText(eStr);
            msgLabel.setToolTipText(eStr);
        }
    }

    private void encode() {
        String text = base64Text.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;
        String result = BaseEncoding.base64().encode(text.getBytes());
        base64Text.setText(result);
        msgLabel.setText("base64 encode");
        msgLabel.setToolTipText("base64 encode");
    }

    private void decode() {
        String text = base64Text.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;
        String result = new String(BaseEncoding.base64().decode(text));
        base64Text.setText(result);
        msgLabel.setText("base64 decode");
        msgLabel.setToolTipText("base64 decode");
    }

    private void sqlFormat() {
        String text = sqlText.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;
        String result = SqlFormat.format(text);
        if (null == result) {
            msgLabel.setText("sql format error!");
            return;
        }
        sqlText.setText(result);
        msgLabel.setText("sql format");
        msgLabel.setToolTipText("sql format");
    }

    private void zhToUnicode() {
        String text = unicodeText.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;
        String result = UnicodeUtil.unicodeEncode(text);
        if (null == result) {
            msgLabel.setText("unicode encode error!");
            return;
        }
        unicodeText.setText(result);
        msgLabel.setText("unicode encode!");
        msgLabel.setToolTipText("unicode encode!");
    }

    private void unicodeToZh() {
        String text = unicodeText.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;
        String result = UnicodeUtil.unicodeDecode(text);
        if (null == result) {
            msgLabel.setText("unicode decode error!");
            return;
        }
        unicodeText.setText(result);
        msgLabel.setText("unicode decode!");
        msgLabel.setToolTipText("unicode decode!");
    }

    private void createRSyntaxTextArea() {
        jsonText = createArea(JSON);
        RTextScrollPane jsonSp = new RTextScrollPane(jsonText);
        jsonSp.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
        jsPanel.add(jsonSp);

        xmlText = createArea(XML);
        RTextScrollPane xmlSp = new RTextScrollPane(xmlText);
        xmlSp.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
        xmlPanel.add(xmlSp);

        htmlText = createArea(HTML);
        RTextScrollPane htmlSp = new RTextScrollPane(htmlText);
        htmlSp.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
        htmlPanel.add(htmlSp);

        sqlText = createArea(SQL);
        RTextScrollPane sqlSp = new RTextScrollPane(sqlText);
        sqlSp.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
        sqlPanel.add(sqlSp);
    }

    private RSyntaxTextArea createArea(String type) {
        RSyntaxTextArea area = new RSyntaxTextArea();
        if ("JSON".equalsIgnoreCase(type))
            area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
        else if ("XML".equalsIgnoreCase(type))
            area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        else if ("HTML".equalsIgnoreCase(type))
            area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML);
        else if ("SQL".equalsIgnoreCase(type))
            area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
        area.setCodeFoldingEnabled(true);
        area.setAntiAliasingEnabled(true);
        area.setAutoscrolls(true);

        SyntaxScheme scheme = area.getSyntaxScheme();
        scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.BLUE;
        scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = new Color(164, 0, 0);
        scheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground = new Color(164, 0, 0);
        scheme.getStyle(Token.LITERAL_BOOLEAN).foreground = Color.RED;
        scheme.getStyle(Token.OPERATOR).foreground = Color.BLACK;
        area.revalidate();
        area.addMouseListener(new TextAreaMouseListener());
        return area;
    }

    private JTextArea getTextArea() {
        int selIndex = tabbedPane1.getSelectedIndex();
        if (selIndex >= 0) {
            JPanel sp = (JPanel) tabbedPane1.getComponentAt(selIndex);
            RTextScrollPane rp = (RTextScrollPane) sp.getComponent(0);
            JViewport vp = (JViewport) rp.getComponent(0);
            JTextArea ta = (JTextArea) vp.getComponent(0);
            System.out.println(ta.getText());
            return ta;
        }
        return null;
    }

    private class TextAreaMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                JPopupMenu popMenu = new JPopupMenu();
                JMenuItem mtCopy = new JMenuItem("\u590d\u5236");
                JMenuItem mtPaste = new JMenuItem("\u7c98\u5e16");
                JMenuItem mtSelAll = new JMenuItem("\u5168\u9009");
                JMenuItem mtClean = new JMenuItem("\u6e05\u7a7a");

                popMenu.add(mtCopy);
                popMenu.add(mtPaste);
                popMenu.add(mtSelAll);
                popMenu.add(mtClean);
                JTextArea ta = getTextArea();
                if (ta.getSelectedText() == null || ta.getSelectedText().length() == 0) {
                    mtCopy.setEnabled(false);
                }

                mtCopy.addActionListener(new TextAreaMenuItemActionListener(1));
                mtPaste.addActionListener(new TextAreaMenuItemActionListener(2));
                mtSelAll.addActionListener(new TextAreaMenuItemActionListener(3));
                mtClean.addActionListener(new TextAreaMenuItemActionListener(4));
                popMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }


    private class TextAreaMenuItemActionListener implements ActionListener {
        private int optType;
        private String str;
        private JTextArea area;

        //optType 1:复制;2:粘帖;3:全选;4:清空
        public TextAreaMenuItemActionListener(int optType) {
            this.optType = optType;
        }

        public void actionPerformed(ActionEvent e) {
            area = getTextArea();
            if (optType == 1) {
                area.copy();
            } else if (optType == 2) {
                area.paste();
            } else if (optType == 3) {
                area.selectAll();
            } else if (optType == 4) {
                area.setText("");
            }
        }
    }
}
