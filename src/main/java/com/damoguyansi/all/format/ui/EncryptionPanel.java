package com.damoguyansi.all.format.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EncryptionPanel extends JPanel {
    private JPanel panel1;
    private JRadioButton AESRadioButton;
    private JRadioButton RSARadioButton;
    private JRadioButton MD5RadioButton;
    private JRadioButton SM2RadioButton;
    private JRadioButton SM3RadioButton;
    private JRadioButton SM4RadioButton;
    private JTextArea keyTextArea;
    private JButton enButton;
    private JButton deButton;
    private JTextArea encryptInText;
    private JTextArea decryptOutText;
    private ButtonGroup buttonGroup;
    private RadioChangeListener radioChangeListener;

    private Color foregColor = new Color(177, 177, 177);

    public EncryptionPanel() {
        encryptInText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        encryptInText.setLineWrap(true);
        decryptOutText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        keyTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        keyTextArea.setLineWrap(true);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(AESRadioButton);
        buttonGroup.add(RSARadioButton);
        buttonGroup.add(MD5RadioButton);
        buttonGroup.add(SM2RadioButton);
        buttonGroup.add(SM3RadioButton);
        buttonGroup.add(SM4RadioButton);

        keyTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if ("请输入密钥".equals(keyTextArea.getText())) {
                    keyTextArea.setText("");
                    keyTextArea.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if ("".equals(keyTextArea.getText())) {
                    keyTextArea.setForeground(new Color(177, 177, 177));
                    keyTextArea.setText("请输入密钥");
                }
            }
        });

        radioChangeListener = new RadioChangeListener();
        MD5RadioButton.addItemListener(radioChangeListener);
        SM3RadioButton.addItemListener(radioChangeListener);
        AESRadioButton.addItemListener(radioChangeListener);
        SM4RadioButton.addItemListener(radioChangeListener);
        RSARadioButton.addItemListener(radioChangeListener);
        SM2RadioButton.addItemListener(radioChangeListener);

        keyTextArea.setForeground(foregColor);
        keyTextArea.setText("无需入密钥");
        keyTextArea.setEditable(false);
        System.out.println(encryptInText.getSize());
    }

    class RadioChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            JRadioButton button = (JRadioButton) e.getSource();
            String text = button.getText();
            System.out.println(text);
            if (button.isSelected()) {
                if (("MD5".equalsIgnoreCase(text) || "SM3".equalsIgnoreCase(text)) && keyTextArea.isVisible()) {
                    keyTextArea.setForeground(foregColor);
                    keyTextArea.setText("无需入密钥");
                    keyTextArea.setEditable(false);
                } else {
                    keyTextArea.setForeground(foregColor);
                    keyTextArea.setText("请输入密钥");
                    keyTextArea.setEditable(true);
                }
            }
        }
    }
}
