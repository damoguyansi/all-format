package com.damoguyansi.all.format.toolwindow;

import com.damoguyansi.all.format.constant.Constants;
import com.damoguyansi.all.format.dialog.TranslateDialog;
import com.damoguyansi.all.format.i18n.I18n;
import com.damoguyansi.all.format.settings.AppSettings;
import com.damoguyansi.all.format.util.ColorUtil;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.ActionLink;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * 把 AllFormat 面板嵌入工具窗口，并在底部左侧放一个「Star 支持一下」入口。
 *
 * @author damoguyansi
 */
public class AllFormatToolWindowFactory implements ToolWindowFactory, DumbAware {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JPanel root = new JPanel(new BorderLayout());
        TranslateDialog dialog = new TranslateDialog(ColorUtil.isDarcula());
        root.add(dialog.getCenterPanel(), BorderLayout.CENTER);
        root.add(footer(language -> {
            AppSettings settings = AppSettings.getInstance();
            I18n.Language previous = I18n.resolveLanguage(settings.getLanguage(), Locale.getDefault());
            settings.setLanguage(language);
            I18n.Language current = I18n.resolveLanguage(language, Locale.getDefault());
            relocalize(root, previous, current);
            root.revalidate();
            root.repaint();
        }), BorderLayout.SOUTH);

        Content content = ContentFactory.getInstance().createContent(root, "", false);
        content.setDisposer(dialog::dispose);
        toolWindow.getContentManager().addContent(content);
    }

    private void relocalize(Component component, I18n.Language from, I18n.Language to) {
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            button.setText(I18n.translateUiText(button.getText(), from, to));
        } else if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            label.setText(I18n.translateUiText(label.getText(), from, to));
        }
        if (component instanceof JComponent) {
            JComponent swingComponent = (JComponent) component;
            swingComponent.setToolTipText(I18n.translateUiText(swingComponent.getToolTipText(), from, to));
            Object textKey = swingComponent.getClientProperty(I18n.UI_TEXT_KEY);
            if (textKey instanceof String && component instanceof JTextComponent) {
                JTextComponent textComponent = (JTextComponent) component;
                String previousText = I18n.message(from, (String) textKey);
                if (previousText.equals(textComponent.getText())) {
                    textComponent.setText(I18n.message(to, (String) textKey));
                }
            }
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                relocalize(child, from, to);
            }
        }
    }

    private JComponent footer(Consumer<I18n.Language> languageChanged) {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(JBUI.Borders.compound(
                JBUI.Borders.customLine(com.intellij.ui.JBColor.border(), 1, 0, 0, 0),
                JBUI.Borders.empty(4, 8)));

        ActionLink star = new ActionLink(I18n.message("footer.star"));
        star.addActionListener(e -> BrowserUtil.browse(Constants.CODE_GITHUB_URL));
        star.setToolTipText(I18n.message("footer.star.tooltip"));
        footer.add(star, BorderLayout.WEST);

        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        languagePanel.add(new JLabel(I18n.message("footer.language")));
        JComboBox<I18n.Language> languageBox = new JComboBox<>(I18n.Language.values());
        languageBox.setFocusable(false);
        languageBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                           boolean isSelected, boolean cellHasFocus) {
                return super.getListCellRendererComponent(list,
                        value instanceof I18n.Language ? I18n.languageName((I18n.Language) value) : value,
                        index, isSelected, cellHasFocus);
            }
        });
        languageBox.setSelectedItem(AppSettings.getInstance().getLanguage());
        languageBox.addActionListener(e -> {
            I18n.Language selected = (I18n.Language) languageBox.getSelectedItem();
            if (selected != null && selected != AppSettings.getInstance().getLanguage()) {
                languageChanged.accept(selected);
            }
        });
        languagePanel.add(languageBox);
        footer.add(languagePanel, BorderLayout.EAST);
        return footer;
    }
}
