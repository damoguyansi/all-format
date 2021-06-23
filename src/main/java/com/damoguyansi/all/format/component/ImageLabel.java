package com.damoguyansi.all.format.component;

import com.damoguyansi.all.format.util.ClipboardUtil;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ImageLabel extends JLabel implements MouseListener {

    private JTextPane textPane;

    public ImageLabel(JTextPane pane, ImageIcon image) {
        super(image);
        this.textPane = pane;
        this.addMouseListener(this);
    }

    public ImageIcon getImageIcon() {
        return (ImageIcon) this.getIcon();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof JLabel) {
            setLabelBorder(e);
            if (e.isPopupTrigger()) {
                menuItemInit(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed(e);
    }

    private void setLabelBorder(MouseEvent e) {
        for (int i = 0; i < textPane.getDocument().getLength(); i++) {
            Element elem = ((StyledDocument) textPane.getDocument()).getCharacterElement(i);
            AttributeSet as = elem.getAttributes();
            if (as.containsAttribute(AbstractDocument.ElementNameAttribute, StyleConstants.ComponentElementName)) {
                if (StyleConstants.getComponent(as) instanceof JLabel) {
                    ImageLabel myLabel = (ImageLabel) StyleConstants.getComponent(as);
                    myLabel.setBorder(BorderFactory.createEmptyBorder());
                }
            }
        }
        ImageLabel label = (ImageLabel) e.getSource();
        label.setBorder(BorderFactory.createLineBorder(Color.gray));
    }

    private void menuItemInit(MouseEvent e) {
        JPopupMenu popMenu = new JPopupMenu();
        JMenuItem mtCopy = new JMenuItem("\u590d\u5236");
        popMenu.add(mtCopy);
        mtCopy.addActionListener(new ImageLabelMenuItemActionListener(1));
        popMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void getLabelImage() {
        ImageIcon imageIcon = (ImageIcon) this.getIcon();
        ClipboardUtil.setClipboardImage(imageIcon.getImage());
    }

    private class ImageLabelMenuItemActionListener implements ActionListener {
        private int optType;

        public ImageLabelMenuItemActionListener(int optType) {
            this.optType = optType;
        }

        public void actionPerformed(ActionEvent e) {
            if (optType == 1) {
                getLabelImage();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
