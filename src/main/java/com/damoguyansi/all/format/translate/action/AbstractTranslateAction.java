package com.damoguyansi.all.format.translate.action;

import com.damoguyansi.all.format.translate.util.NoticeUtil;
import com.damoguyansi.all.format.util.WordUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author damoguyansi
 * @description: 翻译基础类
 * @create: 2020-06-03 10:15
 **/
public abstract class AbstractTranslateAction extends AnAction {

    public static Editor editor;
    public static Project project;
    public static SelectionModel selectionModel;

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            String selectedText = "";
            if (editor == null) {
                selectedText = TranslateBalloon.origEditorPane.getSelectedText();
                selectedText = StringUtils.isEmpty(selectedText) ? TranslateBalloon.transEditorPane.getSelectedText() : selectedText;
            } else {
                selectionModel = editor.getSelectionModel();
                selectedText = selectionModel.getSelectedText();
            }
            if (StringUtils.isEmpty(selectedText)) {
                NoticeUtil.error("请选择要翻译的字符");
                return;
            }
            NoticeUtil.init(this.getClass().getSimpleName(), 1);
            doTranslate(WordUtil.textToWords(selectedText));
        } catch (Exception e) {
            NoticeUtil.error(e);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        project = e.getData(CommonDataKeys.PROJECT);
        editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setVisible(project != null && editor != null && editor.getSelectionModel().hasSelection());
    }

    /**
     * 执行翻译
     *
     * @param selectText
     * @return
     */
    protected abstract void doTranslate(String selectText);

    /**
     * 文本弹出显示
     *
     * @param result
     * @param translateType
     */
    protected abstract void showPopupBalloon(GoogleTranslateResult result, String translateType);
}
