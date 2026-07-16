package com.damoguyansi.all.format.i18n;

import com.damoguyansi.all.format.settings.AppSettings;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public final class I18n {

    public static final String UI_TEXT_KEY = "allformat.i18n.textKey";

    public enum Language {
        AUTO,
        ZH_CN,
        ENGLISH
    }

    private static final String BUNDLE_NAME = "messages.AllFormatBundle";
    private static final ResourceBundle.Control NO_FALLBACK =
            ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES);

    private I18n() {
    }

    public static String message(String key, Object... params) {
        return message(resolveLanguage(AppSettings.getInstance().getLanguage(), Locale.getDefault()), key, params);
    }

    public static String message(Language language, String key, Object... params) {
        Locale locale = locale(language);
        String pattern = bundle(language).getString(key);
        return params.length == 0 ? pattern : new MessageFormat(pattern, locale).format(params);
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

    public static String translateUiText(String text, Language from, Language to) {
        if (text == null || text.isEmpty() || from == to) {
            return text;
        }
        ResourceBundle source = bundle(from);
        ResourceBundle target = bundle(to);
        for (String key : source.keySet().stream()
                .sorted(Comparator.<String>comparingInt(key -> source.getString(key).length())
                        .reversed().thenComparing(Comparator.naturalOrder()))
                .toList()) {
            String sourceText = source.getString(key);
            int index = text.indexOf(sourceText);
            if (!sourceText.isEmpty() && index >= 0) {
                return text.substring(0, index) + target.getString(key)
                        + text.substring(index + sourceText.length());
            }
        }
        return text;
    }

    public static ResourceBundle bundle(Language language) {
        return ResourceBundle.getBundle(BUNDLE_NAME, locale(language), NO_FALLBACK);
    }
}
