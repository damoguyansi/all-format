package com.damoguyansi.all.format.i18n;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class I18nTest {

    @Test
    void autoLanguageFollowsChineseSystemLocales() {
        assertEquals(I18n.Language.ZH_CN,
                I18n.resolveLanguage(I18n.Language.AUTO, Locale.SIMPLIFIED_CHINESE));
        assertEquals(I18n.Language.ZH_CN,
                I18n.resolveLanguage(I18n.Language.AUTO, Locale.TRADITIONAL_CHINESE));
        assertEquals(I18n.Language.ENGLISH,
                I18n.resolveLanguage(I18n.Language.AUTO, Locale.US));
    }

    @Test
    void explicitLanguageOverridesSystemLocale() {
        assertEquals(I18n.Language.ENGLISH,
                I18n.resolveLanguage(I18n.Language.ENGLISH, Locale.SIMPLIFIED_CHINESE));
        assertEquals(I18n.Language.ZH_CN,
                I18n.resolveLanguage(I18n.Language.ZH_CN, Locale.US));
    }

    @Test
    void englishAndChineseBundlesHaveTheSameKeys() {
        ResourceBundle english = ResourceBundle.getBundle("messages.AllFormatBundle", Locale.ENGLISH);
        ResourceBundle chinese = ResourceBundle.getBundle("messages.AllFormatBundle", Locale.SIMPLIFIED_CHINESE);

        assertEquals(english.keySet(), chinese.keySet());
        english.keySet().forEach(key -> {
            assertFalse(english.getString(key).isBlank(), key + " is blank in English");
            assertFalse(chinese.getString(key).isBlank(), key + " is blank in Chinese");
        });
    }
}
