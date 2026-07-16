package com.damoguyansi.all.format.i18n;

import org.junit.jupiter.api.Test;

import java.util.Locale;

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
        var english = I18n.bundle(I18n.Language.ENGLISH);
        var chinese = I18n.bundle(I18n.Language.ZH_CN);

        assertEquals(english.keySet(), chinese.keySet());
        assertEquals("Language:", english.getString("footer.language"));
        assertEquals("语言：", chinese.getString("footer.language"));
        english.keySet().forEach(key -> {
            assertFalse(english.getString(key).isBlank(), key + " is blank in English");
            assertFalse(chinese.getString(key).isBlank(), key + " is blank in Chinese");
        });
    }

    @Test
    void translatesStaticUiTextWithoutChangingSurroundingLayoutWhitespace() {
        assertEquals("  密码长度：", I18n.translateUiText(
                "  Password length:", I18n.Language.ENGLISH, I18n.Language.ZH_CN));
        assertEquals("Unchanged", I18n.translateUiText(
                "Unchanged", I18n.Language.ENGLISH, I18n.Language.ZH_CN));
    }
}
