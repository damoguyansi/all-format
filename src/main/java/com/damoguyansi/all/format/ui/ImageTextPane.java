package com.damoguyansi.all.format.ui;

import com.sun.media.sound.SoftTuning;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ImageTextPane extends JTextPane {

    public ImageTextPane(){
        System.out.println(".......");
    }

    @Override
    public void paste() {
        System.out.println("=======");
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
}
