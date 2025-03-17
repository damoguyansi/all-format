package com.damoguyansi.all.format.util;

import com.intellij.ide.ui.LafManager;

import java.awt.*;

public class ColorUtil {

    private static final String TRANS_COLOR = "#1C0B93";
    private static final String POS_COLOR = "#3A4A3C";
    private static final String ENTRY_COLOR = "#A945BA";
    private static final String ENTRY_TRANS_COLOR = "#4141E9";

    private static final String TRANS_COLOR_DARCULA = "#FFC66D";
    private static final String POS_COLOR_DARCULA = "#D277EF";
    private static final String ENTRY_COLOR_DARCULA = "#D6D60F";
    private static final String ENTRY_TRANS_COLOR_DARCULA = "#FFC66D";

    public static boolean isDarcula() {
        LafManager lafManager = LafManager.getInstance();
        String feel = lafManager.getCurrentUIThemeLookAndFeel().getName();
        return feel.contains("Darcula") || feel.contains("Dark");
    }

    public static String transColor() {
        if (isDarcula())
            return TRANS_COLOR_DARCULA;
        else {
            return TRANS_COLOR;
        }
    }

    public static String posColor() {
        if (isDarcula())
            return POS_COLOR_DARCULA;
        else
            return POS_COLOR;
    }

    public static String entryColor() {
        if (isDarcula())
            return ENTRY_COLOR_DARCULA;
        else
            return ENTRY_COLOR;
    }

    public static String entryTransColor() {
        if (isDarcula())
            return ENTRY_TRANS_COLOR_DARCULA;
        else
            return ENTRY_TRANS_COLOR;
    }

    private static String toHexColor(Color color) {
        String r, g, b;
        StringBuilder su = new StringBuilder();
        r = Integer.toHexString(color.getRed());
        g = Integer.toHexString(color.getGreen());
        b = Integer.toHexString(color.getBlue());
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;
        r = r.toUpperCase();
        g = g.toUpperCase();
        b = b.toUpperCase();
        su.append("#");
        su.append(r);
        su.append(g);
        su.append(b);
        return su.toString();
    }
}
