package com.damoguyansi.all.format;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

public class FormatAction extends AnAction {
    public FormatAction() {
        super("AllFormat");
    }

    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("AllFormat");
        if (toolWindow != null) {
            toolWindow.activate(null);
        }
    }
}
