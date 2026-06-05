package com.damoguyansi.all.format.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * AllFormat 设置页（Settings / Preferences → Tools → AllFormat）。
 *
 * @author damoguyansi
 */
public class AppSettingsConfigurable implements Configurable {

    private JSpinner autoTranslateDelaySpinner;
    private JBCheckBox smartClipboardBox;

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "AllFormat";
    }

    @Override
    public @Nullable JComponent createComponent() {
        autoTranslateDelaySpinner = new JSpinner(new SpinnerNumberModel(700, 0, 5000, 100));
        smartClipboardBox = new JBCheckBox("打开时根据剪贴板内容自动跳到对应标签页");

        JComponent panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("自动翻译防抖（毫秒，0 关闭）："), autoTranslateDelaySpinner, 1, false)
                .addComponent(smartClipboardBox, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();

        reset();
        return panel;
    }

    @Override
    public boolean isModified() {
        AppSettings s = AppSettings.getInstance();
        return ((Integer) autoTranslateDelaySpinner.getValue()) != s.getAutoTranslateDelay()
                || smartClipboardBox.isSelected() != s.isSmartClipboard();
    }

    @Override
    public void apply() {
        AppSettings s = AppSettings.getInstance();
        s.setAutoTranslateDelay((Integer) autoTranslateDelaySpinner.getValue());
        s.setSmartClipboard(smartClipboardBox.isSelected());
    }

    @Override
    public void reset() {
        AppSettings s = AppSettings.getInstance();
        autoTranslateDelaySpinner.setValue(s.getAutoTranslateDelay());
        smartClipboardBox.setSelected(s.isSmartClipboard());
    }
}
