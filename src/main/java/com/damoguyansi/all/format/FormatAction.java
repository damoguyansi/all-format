package com.damoguyansi.all.format;

import com.damoguyansi.all.format.dialog.TranslateDialog;
import com.damoguyansi.all.format.util.ColorUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;

public class FormatAction extends AnAction {
    public FormatAction() {
        super("AllFormat");
    }

    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;

        TranslateDialog dialog = new TranslateDialog(ColorUtil.isDarcula());
    }
}