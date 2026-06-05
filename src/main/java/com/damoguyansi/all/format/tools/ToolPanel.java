package com.damoguyansi.all.format.tools;

import javax.swing.*;

/**
 * 一个独立的工具子面板：自带输入/输出与操作按钮，作为一个标签页挂到主窗口。
 *
 * @author damoguyansi
 */
public interface ToolPanel {

    /** 标签页标题。 */
    String title();

    /** 标签页内容组件。 */
    JComponent component();
}
