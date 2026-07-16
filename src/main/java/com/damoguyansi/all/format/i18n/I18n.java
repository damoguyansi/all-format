package com.damoguyansi.all.format.i18n;

import com.damoguyansi.all.format.settings.AppSettings;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public final class I18n {

    public enum Language {
        AUTO,
        ZH_CN,
        ENGLISH
    }

    private static final String BUNDLE_NAME = "messages.AllFormatBundle";

    private I18n() {
    }

    public static String message(String key, Object... params) {
        String pattern = ResourceBundle.getBundle(BUNDLE_NAME, locale()).getString(key);
        return params.length == 0 ? pattern : MessageFormat.format(pattern, params);
    }

    public static Locale locale() {
        return locale(resolveLanguage(AppSettings.getInstance().getLanguage(), Locale.getDefault()));
    }

    public static Language resolveLanguage(Language preference, Locale systemLocale) {
        if (preference != null && preference != Language.AUTO) {
            return preference;
        }
        return Locale.CHINESE.getLanguage().equals(systemLocale.getLanguage())
                ? Language.ZH_CN : Language.ENGLISH;
    }

    public static Locale locale(Language language) {
        return language == Language.ZH_CN ? Locale.SIMPLIFIED_CHINESE : Locale.ENGLISH;
    }

    public static String languageName(Language language) {
        return message("language." + language.name().toLowerCase(Locale.ROOT));
    }
}
