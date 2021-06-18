package com.damoguyansi.all.format.translate.ui.base;

import java.awt.*;

/**
 * @author damoguyansi
 * @description:
 * @create: 2020-06-10 12:30
 **/
public class InteractionColors {
    public final Color[] normal;
    public final Color[] rollover;
    public final Color[] pressed;
    public final Color[] disabled;

    public InteractionColors(Color[] normal, Color[] rollover, Color[] pressed, Color[] disabled) {
        this.normal = normal;
        this.rollover = rollover;
        this.pressed = pressed;
        this.disabled = disabled;
    }
}
