package com.damoguyansi.all.format.ui;

import javax.swing.*;

public class EncryptPanel extends JPanel {
    private JPanel enRootPanel;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JRadioButton zyRadioButton;
    private JRadioButton dcRadioButton;
    private JRadioButton fdcRadioButton;
    private JComboBox comboBox1;
    private JRadioButton SM4RadioButton;
    private JRadioButton RSARadioButton;
    private JRadioButton SM2RadioButton;

    public EncryptPanel() {

    }

    public void setLineWrap(boolean flag) {
        textArea1.setLineWrap(flag);
        textArea2.setLineWrap(flag);
        comboBox1.setModel(new DefaultComboBoxModel(new String[]{"MD5","SM3"}));
    }
}
