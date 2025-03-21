package com.damoguyansi.all.format.translate;

import com.damoguyansi.all.format.component.balloon.TranslateBalloon;

public class TranslateBalloonThread extends Thread {
    private TranslateBalloon translateBalloon = null;

    public TranslateBalloonThread(TranslateBalloon translateBalloon) {
        this.translateBalloon = translateBalloon;
    }

    @Override
    public void run() {
        translateBalloon.showTranslate();
    }
}
