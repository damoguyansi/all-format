package com.damoguyansi.all.format;

import java.awt.*;

import com.damoguyansi.all.format.ui.MainDialog;
import com.damoguyansi.all.format.ui.NewDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

public class FormatAction extends AnAction {
	public FormatAction() {
		super("AllFormat");
	}

	public void actionPerformed(AnActionEvent event) {
		Project project = event.getProject();
		Application application = ApplicationManager.getApplication();
		Editor editor = event.getData(PlatformDataKeys.EDITOR);

//		MainDialog dialog = new MainDialog(editor == null ? null : editor.getComponent().getBackground());
		NewDialog dialog = new NewDialog();
	}
}