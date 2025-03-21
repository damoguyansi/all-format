package com.damoguyansi.all.format.component.balloon;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.JBColor;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public final class ProcessComponent extends JPanel implements Disposable {
    private final ProcessIcon icon;
    private final JLabel label;
    private final boolean isRunning;

    public final boolean isRunning() {
        return this.isRunning;
    }

    public final String getText() {
        return this.label.getText();
    }

    public final void setText(String value) {
        this.label.setText(value);
    }

    public final void resume() {
        this.icon.resume();
    }

    public final void suspend() {
        this.icon.suspend();
    }

    public void dispose() {
        Disposer.dispose((Disposable) this.icon);
    }

    public ProcessComponent(String text, Insets insets) {
        super();
        this.icon = new ProcessIcon();
        JLabel jLabel = new JLabel(text);
        jLabel.setFont((Font) JBFont.create(jLabel.getFont().deriveFont(18.0F)));
        jLabel.setForeground((Color) (new JBColor(5000268, 13487565)));
        this.label = jLabel;
        this.isRunning = this.icon.isRunning();
        this.setOpaque(false);
        this.setLayout(new GridLayoutManager(1, 2, insets, JBUI.scale(10), 0));

        GridConstraints gridConstraints = new GridConstraints();
        gridConstraints.setColumn(0);
        gridConstraints.setHSizePolicy(0);
        gridConstraints.setVSizePolicy(0);
        gridConstraints.setAnchor(4);
        this.add(this.icon, gridConstraints);

        gridConstraints = new GridConstraints();

        gridConstraints.setColumn(1);
        gridConstraints.setHSizePolicy(0);
        gridConstraints.setVSizePolicy(0);
        gridConstraints.setAnchor(8);
        this.add(this.label, gridConstraints);
    }
}
