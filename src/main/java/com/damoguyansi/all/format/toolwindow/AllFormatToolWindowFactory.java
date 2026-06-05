package com.damoguyansi.all.format.toolwindow;

import com.damoguyansi.all.format.constant.Constants;
import com.damoguyansi.all.format.dialog.TranslateDialog;
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

/**
 * 把 AllFormat 面板嵌入工具窗口，并在底部左侧放一个「Star 支持一下」入口。
 *
 * @author damoguyansi
 */
public class AllFormatToolWindowFactory implements ToolWindowFactory, DumbAware {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        TranslateDialog dialog = new TranslateDialog(ColorUtil.isDarcula());

        JPanel root = new JPanel(new BorderLayout());
        root.add(dialog.getCenterPanel(), BorderLayout.CENTER);
        root.add(footer(), BorderLayout.SOUTH);

        Content content = ContentFactory.getInstance().createContent(root, "", false);
        content.setDisposer(dialog::dispose);
        toolWindow.getContentManager().addContent(content);
    }

    private JComponent footer() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(JBUI.Borders.compound(
                JBUI.Borders.customLine(com.intellij.ui.JBColor.border(), 1, 0, 0, 0),
                JBUI.Borders.empty(4, 8)));

        ActionLink star = new ActionLink("⭐ Star 支持一下");
        star.addActionListener(e -> BrowserUtil.browse(Constants.CODE_GITHUB_URL));
        star.setToolTipText("去 GitHub 给项目点个 Star，支持作者持续更新");
        footer.add(star, BorderLayout.WEST);
        return footer;
    }
}
