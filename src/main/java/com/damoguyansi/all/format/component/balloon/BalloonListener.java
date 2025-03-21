package com.damoguyansi.all.format.component.balloon;

import com.intellij.openapi.ui.popup.JBPopupListener;
import com.intellij.openapi.ui.popup.LightweightWindowEvent;
import org.jetbrains.annotations.NotNull;

public class BalloonListener implements JBPopupListener {
    @Override
    public void onClosed(@NotNull LightweightWindowEvent event) {
        BalloonManager.instance().set(null);
        JBPopupListener.super.onClosed(event);
    }

    @Override
    public void beforeShown(@NotNull LightweightWindowEvent event) {
        BalloonManager.instance().set(event.asBalloon());
        JBPopupListener.super.beforeShown(event);
    }
}
