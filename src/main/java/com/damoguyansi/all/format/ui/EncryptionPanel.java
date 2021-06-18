package com.damoguyansi.all.format.ui;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Locale;

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

    private static final String MD5 = "MD5";
    private static final String SM3 = "SM3";
    private static final String AES = "AES";
    private static final String SM4 = "SM4";
    private static final String RSA = "RAS";
    private static final String SM2 = "SM2";

    public EncryptionPanel() {
        encryptInText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        decryptOutText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        keyTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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
                    keyTextArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if ("".equals(keyTextArea.getText())) {
                    keyTextArea.setForeground(foregColor);
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

        enButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    calcEncrypt(1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        deButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    calcEncrypt(2);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private String getSelectType() {
        String type = "";
        if (MD5RadioButton.isSelected()) {
            type = MD5;
        } else if (SM3RadioButton.isSelected()) {
            type = SM3;
        } else if (AESRadioButton.isSelected()) {
            type = AES;
        } else if (SM4RadioButton.isSelected()) {
            type = SM4;
        } else if (RSARadioButton.isSelected()) {
            type = RSA;
        } else if (SM2RadioButton.isSelected()) {
            type = SM2;
        }
        return type;
    }

    public void calcEncrypt(int falg) throws Exception {
        String enText = encryptInText.getText();
        String key = keyTextArea.getText();
        String deText = decryptOutText.getText();
        String type = getSelectType();

        if (1 == falg && (null == enText || "".equalsIgnoreCase(enText))) throw new Exception("明文不能为空");
        if (2 == falg && (null == deText || "".equalsIgnoreCase(deText))) throw new Exception("密文不能为空");
        if (!MD5.equalsIgnoreCase(type) && !SM3.equalsIgnoreCase(type) && StrUtil.isBlank(key))
            if (StrUtil.isBlank(key)) throw new Exception("KEY不能为空");

        switch (type) {
            case MD5:
                if (1 == falg)
                    decryptOutText.setText(SecureUtil.md5(enText).toUpperCase(Locale.ROOT));
                else {
                    if (StrUtil.isBlank(enText))
                        encryptInText.setText("无");
                }
                break;
            case SM3:
                if (1 == falg)
                    decryptOutText.setText(SmUtil.sm3(enText));
                else {
                    if (StrUtil.isBlank(enText))
                        encryptInText.setText("无");
                }
                break;
            case AES:
                AES aes = SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8));
                if (1 == falg) {
                    decryptOutText.setText(aes.encryptHex(enText.getBytes(StandardCharsets.UTF_8)));
                } else {
                    encryptInText.setText(aes.decryptStr(deText.getBytes(StandardCharsets.UTF_8)));
                }
                break;
            case SM4:
                SymmetricCrypto sm4 = SmUtil.sm4(key.getBytes(StandardCharsets.UTF_8));
                String encryptHex = sm4.encryptHex(enText);
                String decryptSM4Str = sm4.decryptStr(deText, CharsetUtil.CHARSET_UTF_8);
                if (1 == falg)
                    decryptOutText.setText(encryptHex);
                else {
                    encryptInText.setText(decryptSM4Str);
                }
                break;
            case RSA:
                break;
            case SM2:
                KeyPair pair = SecureUtil.generateKeyPair(key);
                byte[] privateKey = pair.getPrivate().getEncoded();
                byte[] publicKey = pair.getPublic().getEncoded();

                SM2 sm2 = SmUtil.sm2(privateKey, publicKey);

                // 公钥加密，私钥解密
                String encryptStr = sm2.encryptBcd(enText, KeyType.PublicKey);
                String decryptSM2Str = StrUtil.utf8Str(sm2.decryptFromBcd(deText, KeyType.PrivateKey));
                if (1 == falg)
                    decryptOutText.setText(encryptStr);
                else {
                    encryptInText.setText(decryptSM2Str);
                }
                break;
        }
    }

    class RadioChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            JRadioButton button = (JRadioButton) e.getSource();
            String text = button.getText();
            if (button.isSelected()) {
                System.out.println(text);
                if ((MD5.equalsIgnoreCase(text) || SM3.equalsIgnoreCase(text)) && keyTextArea.isVisible()) {
                    keyTextArea.setForeground(foregColor);
                    keyTextArea.setText("无需输入密钥");
                } else {
                    keyTextArea.setForeground(foregColor);
                    keyTextArea.setText("请输入密钥");
                }
            }
        }
    }
}
