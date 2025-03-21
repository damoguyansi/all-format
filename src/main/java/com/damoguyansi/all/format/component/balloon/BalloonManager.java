package com.damoguyansi.all.format.component.balloon;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.util.Ref;

@Service
public final class BalloonManager implements Disposable {
    private Ref<Balloon> balloonRef = Ref.create();

    @Override
    public void dispose() {
    }

    public Balloon get() {
        return balloonRef.get();
    }

    public void set(Balloon balloon) {
        balloonRef.set(balloon);
    }

    public static BalloonManager instance() {
        return ApplicationManager.getApplication().getService(BalloonManager.class);
    }
}
