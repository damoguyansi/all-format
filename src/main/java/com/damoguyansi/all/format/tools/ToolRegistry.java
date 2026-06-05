package com.damoguyansi.all.format.tools;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 工具标签页注册表：把所有独立工具面板挂到主窗口的 {@link JTabbedPane}。
 * 新增工具只需在 {@link #create()} 里加一行。
 *
 * @author damoguyansi
 */
public final class ToolRegistry {

    private static final Set<String> TITLES = new LinkedHashSet<>();

    private ToolRegistry() {
    }

    private static List<ToolPanel> create() {
        List<ToolPanel> tools = new ArrayList<>();
        tools.add(new JsonToolPanel());
        tools.add(new TranslateToolPanel());
        tools.add(new Base64ToolPanel());
        tools.add(new EncodeToolPanel());
        tools.add(new QrCodeToolPanel());
        tools.add(new GeneratorToolPanel());
        tools.add(new HashToolPanel());
        tools.add(new JsonYamlToolPanel());
        tools.add(new DiffToolPanel());
        return tools;
    }

    /** 把所有工具作为标签页追加到给定的 TabbedPane。 */
    public static void install(JTabbedPane tabbedPane) {
        for (ToolPanel tool : create()) {
            TITLES.add(tool.title());
            tabbedPane.addTab(tool.title(), tool.component());
        }
    }

    /** 是否为独立工具标签页（用于隐藏主窗口底部共享按钮）。 */
    public static boolean isTool(String title) {
        return TITLES.contains(title);
    }
}
