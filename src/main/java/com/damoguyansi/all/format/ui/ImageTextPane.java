package com.damoguyansi.all.format.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ImageTextPane extends JTextPane implements ActionListener {
    private final Set<Integer> pressed = new HashSet<Integer>();

    public ImageTextPane() {
        KeyStroke copy1 = KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK, false);
        KeyStroke copy2 = KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.META_MASK, false);
        this.registerKeyboardAction(this, "Paste", copy1, JComponent.WHEN_FOCUSED);
        this.registerKeyboardAction(this, "Paste", copy2, JComponent.WHEN_FOCUSED);
    }

    @Override
    public void paste() {
        if (isEditable() && isEnabled()) {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = cb.getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                super.paste();
            } else if (contents != null && contents.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                try {
                    Image image = (Image) contents.getTransferData(DataFlavor.imageFlavor);
                    ImageIcon ii = new ImageIcon(image);
                    ImageLabel label = new ImageLabel(this, ii);
                    insertComponent(label);
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        System.out.println(e.getActionCommand());
        if (e.getActionCommand().compareTo("Paste") == 0) {
            this.paste();
        }
    }
}
