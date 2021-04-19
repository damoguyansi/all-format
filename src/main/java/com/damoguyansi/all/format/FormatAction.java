package com.damoguyansi.all.format;

import com.damoguyansi.all.format.ui.NewDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.util.ui.UIUtil;

import java.awt.*;

public class FormatAction extends AnAction {
    public FormatAction() {
        super("AllFormat");
    }

    public void actionPerformed(AnActionEvent event) {
        Color color = null;
        if (true == UIUtil.isUnderDarcula()) {
            color = new Color(43, 43, 43);
        } else {
            color = new Color(255, 255, 255);
        }
        NewDialog dialog = new NewDialog(color);
    }
}