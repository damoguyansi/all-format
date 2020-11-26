package com.damoguyansi.all.format;

import com.damoguyansi.all.format.ui.NewDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.UIUtil;

import java.awt.*;

public class FormatAction extends AnAction {
    public FormatAction() {
        super("AllFormat");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        Application application = ApplicationManager.getApplication();

        Color color = UIUtil.getEditorPaneBackground();
        if (255 != color.getRed()) {
            color = new Color(43, 43, 43);
        } else {
            color = null;
        }

//		MainDialog dialog = new MainDialog(editor == null ? null : editor.getComponent().getBackground());
        NewDialog dialog = new NewDialog(color);
    }
}