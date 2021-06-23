package com.damoguyansi.all.format.translate.component;

import com.damoguyansi.all.format.translate.bean.GTResult;
import com.damoguyansi.all.format.util.NoticeUtil;
import com.damoguyansi.all.format.util.TranslateUtil;
import com.intellij.ide.IdeTooltipManager;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.HintHint;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.panels.NonOpaquePanel;
import com.intellij.util.Alarm;
import com.intellij.util.ui.JBFont;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import java.awt.*;

public class TranslateBalloon implements Disposable {
    private final String PANEL_PROCESSING = "processing";
    private final String PANEL_TRANSLATION = "translation";

    private Alarm alarm = new Alarm(Alarm.ThreadToUse.SWING_THREAD);
    private Balloon balloon = null;
    private JBPanel contentPanel = new JBPanel<JBPanel>();

    private CardLayout layout = new CardLayout();
    private ProcessComponent processPane = new ProcessComponent("", JBUI.insets(20));
    private JPanel translatePane = new JPanel();

    private String selectText;
    private String translateType;

    private boolean disposed = false;

    public TranslateBalloon(String selectText, String translateType) {
        this.translateType = translateType;
        this.selectText = selectText;
        initLayout();
    }

    private void initLayout() {
        contentPanel = new JBPanel(layout);
        contentPanel.withFont(JBFont.create(UIUtil.getLabelFont(UIUtil.FontSize.NORMAL)));
        contentPanel.andTransparent();
        contentPanel.add(PANEL_PROCESSING, processPane);
        contentPanel.add(PANEL_TRANSLATION, translatePane);
    }

    private void initTranslatePane(String html) {
        Color fillColor = JBUI.CurrentTheme.CustomFrameDecorations.paneBackground();
        JEditorPane text = IdeTooltipManager.initPane(html, new HintHint().setTextFg(null).setAwtTooltip(true), null);

        text.setEditable(false);
        NonOpaquePanel.setTransparent(text);
        text.setBorder(null);

        JLabel label = new JLabel();
        translatePane = new NonOpaquePanel(new BorderLayout((int) (label.getIconTextGap() * 1.5), (int) (label.getIconTextGap() * 1.5)));

        final NonOpaquePanel textWrapper = new NonOpaquePanel(new GridBagLayout());
        JScrollPane scrolledText = ScrollPaneFactory.createScrollPane(text, true);
        scrolledText.setBackground(fillColor);
        scrolledText.getViewport().setBackground(fillColor);
        textWrapper.add(scrolledText);
        translatePane.add(textWrapper, BorderLayout.CENTER);

        translatePane.setBorder(JBUI.Borders.empty(2, 4));
        contentPanel.add(PANEL_TRANSLATION, translatePane);
    }

    public void showTranslate() {
        String resultStr = "";
        try {
            GTResult gtResult = TranslateUtil.translate(selectText, translateType);
            resultStr = gtResult.toString();
        } catch (Exception e) {
            resultStr = e.getMessage();
            e.printStackTrace();
        }
        initTranslatePane(resultStr);
        NoticeUtil.info(translateType, resultStr);
        contentPanel.revalidate();
        alarm.addRequest(new Runnable() {
            @Override
            public void run() {
                try {
                    layout.show(contentPanel, PANEL_TRANSLATION);
                    if (!balloon.isDisposed())
                        balloon.revalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 200);
    }

    public Balloon createBalloon() {
        balloon = new TranslateBalloonBuilder(contentPanel).setDialogMode(true)
                .setFillColor(JBUI.CurrentTheme.CustomFrameDecorations.paneBackground())
                .setBorderColor(JBUI.CurrentTheme.CustomFrameDecorations.paneBackground())
                .setShadow(true)
                .setAnimationCycle(200)
                .setHideOnClickOutside(true)
                .setHideOnAction(true)
                .setHideOnFrameResize(true)
                .setHideOnCloseClick(true)
                .setHideOnKeyOutside(true)
                .setBlockClicksThroughBalloon(true)
                .setCloseButtonEnabled(false)
                .createBalloon();
        balloon.addListener(new BalloonListener());
        return balloon;
    }

    public void dispose() {
        if (disposed) {
            return;
        }
        disposed = true;
        balloon.hide();
    }

    public void hide() {
        if (!disposed) {
            Disposer.dispose((Disposable) this);
        }
    }
}
