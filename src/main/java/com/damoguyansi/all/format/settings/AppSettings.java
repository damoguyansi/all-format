package com.damoguyansi.all.format.settings;

import com.damoguyansi.all.format.i18n.I18n;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

/**
 * 全局持久化设置。
 *
 * @author damoguyansi
 */
@Service(Service.Level.APP)
@State(name = "AllFormatSettings", storages = @Storage("allformat.xml"))
public final class AppSettings implements PersistentStateComponent<AppSettings.State> {

    public static class State {
        /** 自动翻译防抖延迟（毫秒）。0 表示关闭输入即译。 */
        public int autoTranslateDelay = 700;
        /** 打开时根据剪贴板内容自动跳到对应 Tab。 */
        public boolean smartClipboard = true;
        /** 显示语言。AUTO 表示跟随系统语言。 */
        public I18n.Language language = I18n.Language.AUTO;
    }

    private State state = new State();

    public static AppSettings getInstance() {
        return ApplicationManager.getApplication().getService(AppSettings.class);
    }

    @Override
    public @NotNull State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

    public int getAutoTranslateDelay() {
        return state.autoTranslateDelay;
    }

    public void setAutoTranslateDelay(int delay) {
        state.autoTranslateDelay = delay;
    }

    public boolean isSmartClipboard() {
        return state.smartClipboard;
    }

    public void setSmartClipboard(boolean smartClipboard) {
        state.smartClipboard = smartClipboard;
    }

    public I18n.Language getLanguage() {
        return state.language == null ? I18n.Language.AUTO : state.language;
    }

    public void setLanguage(I18n.Language language) {
        state.language = language == null ? I18n.Language.AUTO : language;
    }
}
