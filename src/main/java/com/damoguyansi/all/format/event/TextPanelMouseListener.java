package com.damoguyansi.all.format.event;

import com.damoguyansi.all.format.ui.ImageLabel;
import com.damoguyansi.all.format.util.ClipboardUtil;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextPanelMouseListener extends MouseAdapter {

    private JTabbedPane tabbedPane;

    public TextPanelMouseListener(JTabbedPane jTabbedPane) {
        this.tabbedPane = jTabbedPane;
    }

    private JTextComponent getTextPane() {
        if (null == tabbedPane) return null;
        int selIndex = tabbedPane.getSelectedIndex();
        if (selIndex >= 0) {
            Component c = tabbedPane.getComponentAt(selIndex);
            if (c instanceof JScrollPane) {
                JScrollPane sp = (JScrollPane) c;
                JViewport vp = (JViewport) sp.getComponent(0);
                Component com = vp.getComponent(0);
                if (com instanceof JTextPane) {
                    return (JTextPane) vp.getComponent(0);
                } else {
                    return (JTextArea) vp.getComponent(0);
                }
            } else if (c instanceof JPanel) {
                JPanel sp = (JPanel) c;
                if (sp.getComponent(0) instanceof RTextScrollPane) {
                    RTextScrollPane rp = (RTextScrollPane) sp.getComponent(0);
                    JViewport vp = (JViewport) rp.getComponent(0);
                    JTextArea ta = (JTextArea) vp.getComponent(0);
                    return ta;
                } else if (sp.getComponent(0) instanceof JScrollPane) {
                    JScrollPane jsp = (JScrollPane) sp.getComponent(0);
                    JViewport vp = (JViewport) jsp.getComponent(0);
                    Component com = vp.getComponent(0);
                    if (com instanceof JTextPane) {
                        return (JTextPane) vp.getComponent(0);
                    } else {
                        return (JTextArea) vp.getComponent(0);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            menuItemInit(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            menuItemInit(e);
        } else if (e.getSource() instanceof JTextPane) {
            JTextPane text = (JTextPane) e.getSource();
            iterateLabelCancelBorder(text);
        }
    }

    /**
     * 清除JLabel边框
     *
     * @param tp
     */
    private void iterateLabelCancelBorder(JTextPane tp) {
        for (int i = 0; i < tp.getDocument().getLength(); i++) {
            Element elem = ((StyledDocument) tp.getDocument()).getCharacterElement(i);
            AttributeSet as = elem.getAttributes();
            if (as.containsAttribute(AbstractDocument.ElementNameAttribute, StyleConstants.ComponentElementName)) {
                if (StyleConstants.getComponent(as) instanceof JLabel) {
                    ImageLabel myLabel = (ImageLabel) StyleConstants.getComponent(as);
                    myLabel.setBorder(BorderFactory.createEmptyBorder());
                }
            }
        }
    }

    private void menuItemInit(MouseEvent e) {
        JPopupMenu popMenu = new JPopupMenu();
        JMenuItem mtCopy = new JMenuItem("\u590d\u5236");
        JMenuItem mtPaste = new JMenuItem("\u7c98\u5e16");
        JMenuItem mtSelAll = new JMenuItem("\u5168\u9009");
        JMenuItem mtClean = new JMenuItem("\u6e05\u7a7a");

        popMenu.add(mtCopy);
        popMenu.add(mtPaste);
        popMenu.add(mtSelAll);
        popMenu.add(mtClean);
        JTextComponent ta = getTextPane();
        if (null != ta && (ta.getSelectedText() == null || ta.getSelectedText().length() == 0)) {
            mtCopy.setEnabled(false);
        }

        mtCopy.addActionListener(new TextPanelMenuItemActionListener(1));
        mtPaste.addActionListener(new TextPanelMenuItemActionListener(2));
        mtSelAll.addActionListener(new TextPanelMenuItemActionListener(3));
        mtClean.addActionListener(new TextPanelMenuItemActionListener(4));
        popMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private class TextPanelMenuItemActionListener implements ActionListener {
        private int optType;
        private String str;
        private JTextComponent textPane;

        //optType 1:复制;2:粘帖;3:全选;4:清空
        public TextPanelMenuItemActionListener(int optType) {
            this.optType = optType;
        }

        public void actionPerformed(ActionEvent e) {
            textPane = getTextPane();
            if (null == textPane) return;
            if (optType == 1) {
                textPane.copy();
            } else if (optType == 2) {
                if (5 != tabbedPane.getSelectedIndex()) {
                    textPane.paste();
                    return;
                }
                ClipboardUtil.pasteClipboardContent((JTextPane) textPane);
            } else if (optType == 3) {
                textPane.selectAll();
            } else if (optType == 4) {
                textPane.setText("");
            }
        }
    }
}
