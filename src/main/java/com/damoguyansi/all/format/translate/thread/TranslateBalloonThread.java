package com.damoguyansi.all.format.translate.thread;

import com.damoguyansi.all.format.translate.component.TranslateBalloon;

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
