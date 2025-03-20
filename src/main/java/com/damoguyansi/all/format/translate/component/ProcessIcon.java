package com.damoguyansi.all.format.translate.component;

import com.intellij.ui.IconManager;
import com.intellij.util.ui.AnimatedIcon;

import javax.swing.*;

public final class ProcessIcon extends AnimatedIcon {
    private static final Icon[] ICONS;
    private static final Icon STEP_PASSIVE;

    public ProcessIcon() {
        super("Querying Process", ICONS, STEP_PASSIVE, 400);
    }

    static {
        ICONS = new Icon[9];
        for (int i = 0; i < 9; i++) {
            Icon icon = IconManager.getInstance().getIcon("/icons/spinner/step_" + i + ".svg", ProcessIcon.class);
            ICONS[i] = icon;
        }
        STEP_PASSIVE = IconManager.getInstance().getIcon("/icons/spinner/step_passive.svg", ProcessIcon.class);
    }
}
