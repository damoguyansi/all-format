package com.damoguyansi.all.format;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ImagePasteTest {
    public static void main(String[] args) {
        JTextPane tp = new JTextPane() {
            public void paste() {
                if (isEditable() && isEnabled()) {
                    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable contents = cb.getContents(null);
                    if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        super.paste();
                    } else if (contents != null && contents.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                        try {
                            Image img = (Image) contents.getTransferData(DataFlavor.imageFlavor);
                            Icon icon = new ImageIcon(img);
                            insertIcon(icon);
                        } catch (UnsupportedFlavorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        JFrame f = new JFrame("Ctrl+V to paste (String or Image)");
        f.getContentPane().add(new JScrollPane(tp), BorderLayout.CENTER);
        f.setSize(500, 500);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}