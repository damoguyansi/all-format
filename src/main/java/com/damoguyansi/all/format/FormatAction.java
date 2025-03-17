package com.damoguyansi.all.format;

import com.damoguyansi.all.format.ui.NewDialog;
import com.intellij.ide.ui.LafManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class FormatAction extends AnAction {
    public FormatAction() {
        super("AllFormat");
    }

    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;

        NewDialog dialog = new NewDialog(LafManager.getInstance().getCurrentUIThemeLookAndFeel().isDark());
    }
}