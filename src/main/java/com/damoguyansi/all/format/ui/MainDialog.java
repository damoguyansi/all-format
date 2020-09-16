package com.damoguyansi.all.format.ui;

import com.damoguyansi.all.format.util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;

public class MainDialog extends JFrame {
    private JPanel contentPane;
    private JButton jsonOK;
    private JButton xmlOK;
    private JTextPane editorPane1;
    private JLabel msgLabel;
    private JScrollPane scrollPanel;
    private JButton htmlOK;
    private JButton md5OK;
    private JLabel noticeLabel;
    private JButton qrCodeOK;
    private JCheckBox onTopCheckBox;

    private Color editorBK;

    public MainDialog(Color color) {
        this.editorBK = color;
        setContentPane(contentPane);
        getRootPane().setDefaultButton(jsonOK);

        jsonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jsonOK();
            }
        });

        xmlOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xmlOK();
            }
        });
        htmlOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                htmlOK();
            }
        });
        md5OK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                md5OK();
            }
        });
        qrCodeOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                qrCodeOK();
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

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                editorPane1.setSize(scrollPanel.getSize().width - 2, editorPane1.getHeight());
            }
        });
        if (this.editorBK != null) {
            editorPane1.setBackground(this.editorBK);
        }
        editorPane1.setText(getSysClipboardText());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = HttpClientUtils.get("http://www.javams.com/format/msg");
                    if (null != msg && !"".equals(msg)) {
                        noticeLabel.setText(msg);
                    }
                } catch (Exception e) {

                }
            }
        }).start();
        onTopCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

            }
        });

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
    }

    private void jsonOK() {
        String text = editorPane1.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;

        String json = text.replaceAll("\t", "");
        String resStr = null;
        try {
            resStr = JsonFormatTool.format(json);
            msgLabel.setText("json format");
            editorPane1.setText(resStr);
        } catch (Throwable e) {
            resStr = MapFormat.format(json);
            msgLabel.setText("map format");
            editorPane1.setText(resStr);
        }
    }

    private void xmlOK() {
        String text = editorPane1.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;

        String json = text.replaceAll("\t", "");
        try {
            String resStr = XmlFormat.format(json);
            msgLabel.setText("xml format success");
            editorPane1.setText(resStr);
        } catch (Throwable e) {
            String eStr = "xml format error [" + e.getMessage() + "]";
            msgLabel.setText(eStr);
            msgLabel.setToolTipText(eStr);
        }
    }

    private void htmlOK() {
        String text = editorPane1.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;

        String json = text.replaceAll("\t", "");
        try {
            String resStr = HtmlFormat.format(json);
            msgLabel.setText("html format");
            editorPane1.setText(resStr);
        } catch (Throwable e) {
            String eStr = "html format error [" + e.getMessage() + "]";
            msgLabel.setText(eStr);
            msgLabel.setToolTipText(eStr);
        }
    }

    private void md5OK() {
        String text = editorPane1.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;
        try {
            String md5Str = MD5Util.encoderByMd5(text);
            msgLabel.setText("md5 success");
            editorPane1.setText(md5Str);
        } catch (Throwable t) {
            String eStr = "md5 error [" + t.getMessage() + "]";
            msgLabel.setText(eStr);
            msgLabel.setToolTipText(eStr);
        }
    }

    private void qrCodeOK() {
        String text = editorPane1.getText();
        if (null == text || "".equalsIgnoreCase(text))
            return;

        try {
            editorPane1.setText(text.trim() + "\r\n");
            editorPane1.setCaretPosition(editorPane1.getStyledDocument().getLength());
            editorPane1.insertIcon(new ImageIcon(QrCodeCreateUtil.createQrCode(text.trim(), 250)));
        } catch (Exception e) {
            String eStr = "create qrCode excpeiont [" + e.getMessage() + "]";
            msgLabel.setText(eStr);
            msgLabel.setToolTipText(eStr);
        }
    }

    public static String getSysClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf != null) {
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    ret = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
}
