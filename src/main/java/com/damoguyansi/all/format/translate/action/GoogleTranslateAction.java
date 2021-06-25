package com.damoguyansi.all.format.translate.action;

import com.damoguyansi.all.format.translate.bean.GTResult;
import com.damoguyansi.all.format.translate.component.BalloonManager;
import com.damoguyansi.all.format.translate.component.TranslateBalloon;
import com.damoguyansi.all.format.translate.thread.TranslateBalloonThread;
import com.damoguyansi.all.format.util.NoticeUtil;
import com.damoguyansi.all.format.util.TranslateUtil;
import com.damoguyansi.all.format.util.WordUtil;
import com.intellij.codeInsight.editorActions.SelectWordUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.ui.JBUI;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Google翻译
 *
 * @author damoguyansi
 */
public class GoogleTranslateAction extends AnAction {

    public static Editor editor;
    public static Project project;
    public static SelectionModel selectionModel;

    @Override
    public void update(AnActionEvent e) {
        project = e.getData(CommonDataKeys.PROJECT);
        editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setVisible(project != null && editor != null && editor.getSelectionModel().hasSelection());
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        if (BalloonManager.instance().get() != null) {
            return;
        }

        if (null == editor) {
            return;
        }
        try {
            selectionModel = editor.getSelectionModel();
            String selectedText = selectionModel.getSelectedText();

            if (StringUtils.isEmpty(selectedText)) {
                selectedText = getSelectText();
            }
            if (StringUtils.isEmpty(selectedText)) {
                return;
            }
            NoticeUtil.init(this.getClass().getSimpleName(), 1);
            doTranslate(WordUtil.textToWords(selectedText));
        } catch (Exception e) {
            NoticeUtil.error(e);
        }
    }

    public String getSelectText() {
        List<TextRange> ranges = new ArrayList<>();
        SelectWordUtil.addWordOrLexemeSelection(true, editor, editor.getCaretModel().getOffset(), ranges, SelectWordUtil.JAVA_IDENTIFIER_PART_CONDITION);
        if (null == ranges || ranges.isEmpty()) return null;

        return editor.getDocument().getText(ranges.get(0));
    }

    protected void doTranslate(String selectText) {
        Matcher m = TranslateUtil.p.matcher(selectText.trim());
        String translateType = m.find() ? TranslateUtil.ZH_CN_TO_EN : TranslateUtil.EN_TO_ZH_CN;

        showPopupBalloons(selectText, translateType);
    }

    /**
     * 2个气泡交替显示
     *
     * @param selectText
     * @param translateType
     */
    private void showPopupBalloons(String selectText, String translateType) {
        final JBPopupFactory factory = JBPopupFactory.getInstance();
        TranslateBalloon translateBalloon = new TranslateBalloon(selectText, translateType);
        Balloon balloon = translateBalloon.createBalloon();

        ApplicationManager.getApplication().invokeLater((Runnable) new Runnable() {
            @Override
            public void run() {
                balloon.show(factory.guessBestPopupLocation(editor), Balloon.Position.below);
            }
        });

        TranslateBalloonThread tbt = new TranslateBalloonThread(translateBalloon);
        tbt.start();
    }

    /**
     * 直接显示汽泡
     *
     * @param result
     * @param translateType
     */
    protected void showPopupBalloon(GTResult result, String translateType) {
        ApplicationManager.getApplication().invokeLater((Runnable) new Runnable() {
            @Override
            public void run() {
                Color jbColor = JBUI.CurrentTheme.CustomFrameDecorations.paneBackground();
                final JBPopupFactory factory = JBPopupFactory.getInstance();
                BalloonBuilder balloonBuilder = factory.createHtmlTextBalloonBuilder(result.toString(), null, (Color) jbColor, null);
                Balloon balloon = balloonBuilder
                        .setShadow(true)
                        .setShowCallout(true)
                        .setHideOnAction(true)
                        .setHideOnClickOutside(true)
                        .setHideOnFrameResize(true)
                        .setHideOnKeyOutside(true)
                        .setHideOnLinkClick(true)
                        .setContentInsets(JBUI.insets(30))
                        .setDialogMode(true)
                        .setFillColor(JBUI.CurrentTheme.CustomFrameDecorations.paneBackground())
                        .setBorderColor(JBUI.CurrentTheme.CustomFrameDecorations.paneBackground())
                        .setAnimationCycle(200)
                        .setHideOnCloseClick(true)
                        .setBlockClicksThroughBalloon(true)
                        .setCloseButtonEnabled(false)
                        .createBalloon();
                balloon.show(factory.guessBestPopupLocation(editor), Balloon.Position.below);
            }
        });
    }
}
