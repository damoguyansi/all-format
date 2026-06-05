package com.damoguyansi.all.format.tools;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 文本对比：左右两段文本按行做 LCS 差异，输出统一风格的 +/- 结果。
 *
 * @author damoguyansi
 */
public class DiffToolPanel extends JPanel implements ToolPanel {

    private final JBTextArea left = new JBTextArea();
    private final JBTextArea right = new JBTextArea();
    private final JBTextArea output = new JBTextArea();

    public DiffToolPanel() {
        super(new BorderLayout(0, 6));
        setBorder(JBUI.Borders.empty(8));

        JPanel bar = ToolUi.toolbar();
        bar.add(ToolUi.primaryButton("对比", this::compare));
        JBLabel hint = new JBLabel("左右两段文本按行对比（- 左侧独有 / + 右侧独有）");
        hint.setForeground(JBUI.CurrentTheme.Label.disabledForeground());
        bar.add(hint);
        add(bar, BorderLayout.NORTH);

        left.setLineWrap(true);
        right.setLineWrap(true);
        JSplitPane inputs = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JBScrollPane(left), new JBScrollPane(right));
        inputs.setResizeWeight(0.5);
        inputs.setBorder(JBUI.Borders.empty());

        output.setEditable(false);
        output.setLineWrap(true);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputs, new JBScrollPane(output));
        split.setResizeWeight(0.5);
        split.setBorder(JBUI.Borders.empty());
        add(split, BorderLayout.CENTER);
    }

    @Override
    public String title() {
        return "Diff";
    }

    @Override
    public JComponent component() {
        return this;
    }

    private void compare() {
        String[] a = left.getText().split("\n", -1);
        String[] b = right.getText().split("\n", -1);
        List<String> diff = lcsDiff(a, b);
        if (diff.isEmpty()) {
            output.setText("两段文本完全相同");
        } else {
            output.setText(String.join("\n", diff));
        }
    }

    /** 经典 LCS 动态规划，回溯生成行级差异。 */
    private List<String> lcsDiff(String[] a, String[] b) {
        int n = a.length, m = b.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                dp[i][j] = a[i].equals(b[j]) ? dp[i + 1][j + 1] + 1
                        : Math.max(dp[i + 1][j], dp[i][j + 1]);
            }
        }
        java.util.ArrayList<String> result = new java.util.ArrayList<>();
        boolean changed = false;
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (a[i].equals(b[j])) {
                result.add("  " + a[i]);
                i++;
                j++;
            } else if (dp[i + 1][j] >= dp[i][j + 1]) {
                result.add("- " + a[i++]);
                changed = true;
            } else {
                result.add("+ " + b[j++]);
                changed = true;
            }
        }
        while (i < n) {
            result.add("- " + a[i++]);
            changed = true;
        }
        while (j < m) {
            result.add("+ " + b[j++]);
            changed = true;
        }
        return changed ? result : java.util.Collections.emptyList();
    }
}
