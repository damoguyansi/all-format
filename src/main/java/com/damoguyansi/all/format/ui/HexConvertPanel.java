package com.damoguyansi.all.format.ui;

import com.damoguyansi.all.format.event.NumberTextField;
import com.damoguyansi.all.format.util.NumberUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HexConvertPanel extends JPanel {
    private JTextField twoText;
    private JTextField eightText;
    private JTextField tenText;
    private JTextField sixteenText;
    private JTextField thirtyTwoText;
    private JPanel hPanel1;
    private JRadioButton twoRadioButton;
    private JRadioButton eightRadioButton;
    private JRadioButton tenRadioButton;
    private JRadioButton sixteenRadioButton;
    private JRadioButton thirtyTwoRadioButton;
    private JTextField valueText;
    private JButton convertBtn;
    private NumberTextField twoNumberDoc;
    private NumberTextField eightNumberDoc;
    private NumberTextField tenNumberDoc;
    private NumberTextField sixteenNumberDoc;
    private NumberTextField thirtyTwoNumberDoc;

    private ButtonGroup buttonGroup;
    private ChangeListener radioChangeListener;

    public HexConvertPanel() {
        twoNumberDoc = new NumberTextField(2);
        eightNumberDoc = new NumberTextField(8);
        tenNumberDoc = new NumberTextField(10);
        sixteenNumberDoc = new NumberTextField(16);
        thirtyTwoNumberDoc = new NumberTextField(32);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(twoRadioButton);
        buttonGroup.add(eightRadioButton);
        buttonGroup.add(tenRadioButton);
        buttonGroup.add(sixteenRadioButton);
        buttonGroup.add(thirtyTwoRadioButton);

        valueText.setDocument(tenNumberDoc);

        radioChangeListener = new RadioChangeListener();
        twoRadioButton.addChangeListener(radioChangeListener);
        eightRadioButton.addChangeListener(radioChangeListener);
        tenRadioButton.addChangeListener(radioChangeListener);
        sixteenRadioButton.addChangeListener(radioChangeListener);
        thirtyTwoRadioButton.addChangeListener(radioChangeListener);

        convertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setValue();
            }
        });
    }

    public void setValue() {
        String value = valueText.getText();
        if (null == value || "".equals(value)) return;
        int t = 0;
        if (twoRadioButton.isSelected()) {
            t = NumberUtil.scale2Decimal(value, 2);
        } else if (eightRadioButton.isSelected()) {
            t = NumberUtil.scale2Decimal(value, 8);
        } else if (tenRadioButton.isSelected()) {
            t = NumberUtil.scale2Decimal(value, 10);
        } else if (sixteenRadioButton.isSelected()) {
            t = NumberUtil.scale2Decimal(value, 16);
        } else if (thirtyTwoRadioButton.isSelected()) {
            t = NumberUtil.scale2Decimal(value, 32);
        }
        twoText.setText(NumberUtil.toBinaryString(t));
        eightText.setText(NumberUtil.toOctalString(t));
        tenText.setText(String.valueOf(t));
        sixteenText.setText(NumberUtil.toHexString(t));
        thirtyTwoText.setText(NumberUtil.toTTString(t));
    }

    public void setFocus() {
        valueText.grabFocus();
        valueText.requestFocus();
    }

    class RadioChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            valueText.setText("");
            JRadioButton button = (JRadioButton) e.getSource();
            String text = button.getText();
            if (button.isSelected()) {
                switch (text) {
                    case "2进制":
                        valueText.setDocument(twoNumberDoc);
                        break;
                    case "8进制":
                        valueText.setDocument(eightNumberDoc);
                        break;
                    case "10进制":
                        valueText.setDocument(tenNumberDoc);
                        break;
                    case "16进制":
                        valueText.setDocument(sixteenNumberDoc);
                        break;
                    case "32进制":
                        valueText.setDocument(thirtyTwoNumberDoc);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
