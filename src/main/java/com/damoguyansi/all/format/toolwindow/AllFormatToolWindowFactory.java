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
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;
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
        AtomicReference<TranslateDialog> activeDialog = new AtomicReference<>();
        render(root, activeDialog, -1);

        Content content = ContentFactory.getInstance().createContent(root, "", false);
        content.setDisposer(() -> {
            TranslateDialog dialog = activeDialog.get();
            if (dialog != null) {
                dialog.dispose();
            }
        });
        toolWindow.getContentManager().addContent(content);
    }

    private void render(JPanel root, AtomicReference<TranslateDialog> activeDialog, int selectedIndex) {
        TranslateDialog previous = activeDialog.get();
        TranslateDialog dialog = new TranslateDialog(ColorUtil.isDarcula());
        activeDialog.set(dialog);

        if (selectedIndex >= 0 && selectedIndex < dialog.getTabbedPane().getTabCount()) {
            dialog.getTabbedPane().setSelectedIndex(selectedIndex);
        }

        root.removeAll();
        root.add(dialog.getCenterPanel(), BorderLayout.CENTER);
        root.add(footer(language -> {
            int index = dialog.getTabbedPane().getSelectedIndex();
            AppSettings.getInstance().setLanguage(language);
            render(root, activeDialog, index);
        }), BorderLayout.SOUTH);
        root.revalidate();
        root.repaint();

        if (previous != null) {
            previous.dispose();
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
