package com.damoguyansi.all.format.translate.ui.modules;

import com.damoguyansi.all.format.translate.ui.base.FormColors;
import com.damoguyansi.all.format.translate.ui.base.InteractionColors;

import java.awt.*;

/**
 * @author damoguyansi
 * @description:
 * @create: 2020-06-10 14:59
 **/
public abstract class ColorService {

    private static ColorService INSTANCE = null;

    public static void install(ColorService instance) {
        INSTANCE = instance;
    }

    public static <T> T forCurrentTheme(T[] objects) {
        return INSTANCE.internalForCurrentTheme(objects);
    }

    public static <T> T forLightTheme(T[] objects) {
        if (objects == null) {
            return null;
        } else {
            return objects[0];
        }
    }

    public static <T> T forDarkTheme(T[] objects) {
        if (objects == null) {
            return null;
        } else if (objects.length > 1) {
            return objects[1];
        } else {
            return objects[0];
        }
    }

    public static final Color[] Background = new Color[]{new Color(0xFFFFFF), new Color(0x3C3F41)};
    public static final Color[] EmphasizedText = new Color[]{new Color(0x000000), new Color(0xCBCDCF)};
    public static final Color[] Text = new Color[]{new Color(0x333333), new Color(0x8B8C8F)};
    public static final Color[] UnemphasizedText = new Color[]{new Color(0x88BCCE), new Color(0x8AB0D6)};
    public static final Color[] Separator = new Color[]{new Color(0xE5E5E5), new Color(0x454546)};
    public static final Color[] SelectedTab = new Color[]{new Color(0x666666), new Color(0xCBCDCF)};
    public static final Color[] Tip = new Color[]{new Color(0xE0F2F8), new Color(0x0560A2)};
    public static final Color[] TipText = new Color[]{new Color(0x086a8b), new Color(0xDBEFFF)};
    public static final Color[] ExceptionPreviewText = new Color[]{new Color(0x2985A4), new Color(0x80BEEF)};
    public static final Color[] ExceptionPreviewBackground = new Color[]{new Color(0xC1E7F3), new Color(0x094778)};
    public static final Color[] ScrollbarTrack = new Color[]{new Color(0xF5F5F5), new Color(0x2D2E2F)};
    public static final Color[] ScrollbarThumb = new Color[]{new Color(0xD5D5D5), new Color(0x484A4B)};
    public static final Color[] ErrorBar = new Color[]{new Color(0xF8F4C8), new Color(0xF2EBAB)};
    public static final Color[] LoadingArc = new Color[]{new Color(0x3379A1), new Color(0x61B6E7)};
    public static final Color[] OnlineStatus = new Color[]{new Color(0x6FF800), new Color(0x6FF800)};
    public static final Color[] OfflineStatus = new Color[]{new Color(0xFF1B1B), new Color(0xFF1B1B)};

    public static final FormColors NormalForm = new FormColors(
            new Color[]{new Color(0xB2B2B2), new Color(0x666768)},
            new Color[]{new Color(0x333333), new Color(0xC8C8C9)},
            new Color[]{new Color(0xE80D0D), new Color(0xFF1B1B)},
            new Color[]{new Color(0xFFFFFF), new Color(0x242526)},
            new Color[]{new Color(0x000000), new Color(0xCBCDCF)},
            new Color[]{new Color(0xE80D0D), new Color(0xFF1B1B)},
            new Color[]{new Color(0x333333), new Color(0x8B8C8E)},
            new Color[]{new Color(0xAAAAAA), new Color(0x666768)}
    );

    public static final FormColors TipForm = new FormColors(
            new Color[]{new Color(0xB5D7E2), new Color(0x2072AE)},
            new Color[]{new Color(0x3379A1), new Color(0x61B6E7)},
            new Color[]{new Color(0xE80D0D), new Color(0xFF5644)},
            new Color[]{new Color(0xFFFFFF), new Color(0x083960)},
            new Color[]{new Color(0x000000), new Color(0xDBEFFF)},
            new Color[]{new Color(0xE80D0D), new Color(0xFF5644)},
            new Color[]{new Color(0x086a8b), new Color(0xDBEFFF)},
            new Color[]{new Color(0xAAAAAA), new Color(0x3682BE)}
    );

    public static final InteractionColors LinkInteraction = new InteractionColors(
            new Color[]{new Color(0xFF8000), new Color(0xFF8000)},
            new Color[]{new Color(0xFEA144), new Color(0xFEA144)},
            new Color[]{new Color(0xED7700), new Color(0xED7700)},
            new Color[]{new Color(0xFEA144), new Color(0xFEA144)}
    );

    public static final InteractionColors SecondaryLinkInteraction = new InteractionColors(
            new Color[]{new Color(0x333333), new Color(0xCBCDCF)},
            new Color[]{new Color(0xFEA144), new Color(0xFEA144)},
            new Color[]{new Color(0xED7700), new Color(0xED7700)},
            new Color[]{new Color(0xFEA144), new Color(0xFEA144)}
    );

    public static final InteractionColors MarkInteraction = new InteractionColors(
            new Color[]{new Color(0x3E85DE), new Color(0x7CD2FF)},
            new Color[]{new Color(0x66A6F6), new Color(0xBBE8FF)},
            new Color[]{new Color(0x3E85DE), new Color(0x7CD2FF)},
            new Color[]{new Color(0x81B6C8), new Color(0x78A6D4)}
    );

    protected abstract <T> T internalForCurrentTheme(T[] objects);
}
