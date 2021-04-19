package com.damoguyansi.all.format.event;

import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

public class NumberTextField extends PlainDocument {
    private int type = 10;

    public NumberTextField(int type) {
        super();
        this.type = type;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws javax.swing.text.BadLocationException {
        if (str == null) return;

        char[] s = str.toCharArray();
        int length = 0;

        for (int i = 0; i < s.length; i++) {
            if (2 == type) {
                if ((s[i] >= '0') && (s[i] <= '1')) {
                    s[length++] = s[i];
                }
            } else if (8 == type) {
                if ((s[i] >= '0') && (s[i] <= '8')) {
                    s[length++] = s[i];
                }
            } else if (10 == type) {
                if ((s[i] >= '0') && (s[i] <= '9')) {
                    s[length++] = s[i];
                }
            } else if (16 == type) {
                if (((s[i] >= '0') && (s[i] <= '9'))
                        || ((s[i] >= 'a') && (s[i] <= 'f'))
                        || ((s[i] >= 'A') && (s[i] <= 'F'))) {
                    s[length++] = s[i];
                }
            } else if (32 == type) {
                if (((s[i] >= '0') && (s[i] <= '9'))
                        || ((s[i] >= 'a') && (s[i] <= 'v'))
                        || ((s[i] >= 'A') && (s[i] <= 'V'))) {
                    s[length++] = s[i];
                }
            }
            super.insertString(offset, new String(s, 0, length), attr);
        }
    }
}