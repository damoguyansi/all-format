package com.damoguyansi.all.format.settings;

import com.damoguyansi.all.format.i18n.I18n;
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
    private JComboBox<I18n.Language> languageBox;

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "AllFormat";
    }

    @Override
    public @Nullable JComponent createComponent() {
        autoTranslateDelaySpinner = new JSpinner(new SpinnerNumberModel(700, 0, 5000, 100));
        smartClipboardBox = new JBCheckBox(I18n.message("settings.smartClipboard"));
        languageBox = new JComboBox<>(I18n.Language.values());
        languageBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                    boolean isSelected, boolean cellHasFocus) {
                return super.getListCellRendererComponent(list,
                        value instanceof I18n.Language ? I18n.languageName((I18n.Language) value) : value,
                        index, isSelected, cellHasFocus);
            }
        });

        JComponent panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel(I18n.message("settings.language")), languageBox, 1, false)
                .addLabeledComponent(new JBLabel(I18n.message("settings.autoTranslateDelay")), autoTranslateDelaySpinner, 1, false)
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
                || smartClipboardBox.isSelected() != s.isSmartClipboard()
                || languageBox.getSelectedItem() != s.getLanguage();
    }

    @Override
    public void apply() {
        AppSettings s = AppSettings.getInstance();
        s.setAutoTranslateDelay((Integer) autoTranslateDelaySpinner.getValue());
        s.setSmartClipboard(smartClipboardBox.isSelected());
        s.setLanguage((I18n.Language) languageBox.getSelectedItem());
    }

    @Override
    public void reset() {
        AppSettings s = AppSettings.getInstance();
        autoTranslateDelaySpinner.setValue(s.getAutoTranslateDelay());
        smartClipboardBox.setSelected(s.isSmartClipboard());
        languageBox.setSelectedItem(s.getLanguage());
    }
}
