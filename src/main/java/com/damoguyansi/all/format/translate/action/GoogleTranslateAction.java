package com.damoguyansi.all.format.translate.action;

import com.damoguyansi.all.format.translate.constant.TranslateConstant;
import com.damoguyansi.all.format.translate.util.NoticeUtil;
import com.damoguyansi.all.format.util.TranslateUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.util.ui.JBUI;

import java.awt.*;
import java.util.regex.Matcher;

/**
 * @author damoguyansi
 */
public class GoogleTranslateAction extends AbstractTranslateAction {

    @Override
    protected void doTranslate(String selectText) {
        Matcher m = TranslateConstant.p.matcher(selectText.trim());
        String translateType = m.find() ? TranslateConstant.ZH_CN_TO_EN : TranslateConstant.EN_TO_ZH_CN;
        try {
            GoogleTranslateResult googleTranslateResult = TranslateUtil.translate(selectText, translateType);
            if (googleTranslateResult == null) {
                NoticeUtil.error("翻译错误,请重试!");
                return;
            }
            showPopupBalloon(googleTranslateResult, translateType);
            //NoticeUtil.info(translateType, googleTranslateResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void showPopupBalloon(GoogleTranslateResult result, String translateType) {
        ApplicationManager.getApplication().invokeLater((Runnable) new Runnable() {
            @Override
            public void run() {
                Color jbColor = JBUI.CurrentTheme.CustomFrameDecorations.paneBackground();//new JBColor(0xE4E6EB, 0x45494B);
                final JBPopupFactory factory = JBPopupFactory.getInstance();
                BalloonBuilder balloonBuilder = factory.createHtmlTextBalloonBuilder(result.toString(), null, (Color) jbColor, null);
                balloonBuilder
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
                        .createBalloon()
                        .show(factory.guessBestPopupLocation(editor), Balloon.Position.below);
            }
        });
    }
}
