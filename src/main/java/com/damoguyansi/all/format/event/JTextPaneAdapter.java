package com.damoguyansi.all.format.event;

import com.damoguyansi.all.format.util.ClipboardUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class JTextPaneAdapter implements ActionListener {
    private JTextPane textPane;

    public JTextPaneAdapter(JTextPane myJTable) {
        textPane = myJTable;
        KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.META_MASK, false);
        textPane.registerKeyboardAction(this, "Paste", copy, JComponent.WHEN_FOCUSED);
        System.out.println("register keyboard action...");
    }

    /**
     * 此适配器运行图表的公共读方法。
     */
    public JTextPane getTextPane() {
        return textPane;
    }

    /**
     * 在我们监听此实现的按键上激活这种方法。 此处，它监听复制和粘贴 ActionCommands。 而且此后复制动作无法执行。
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().compareTo("Paste") == 0) {
            System.out.println("copy...");
            ClipboardUtil.pasteClipboardContent(textPane);
        }
    }
}