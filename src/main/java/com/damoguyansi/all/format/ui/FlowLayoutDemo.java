package com.damoguyansi.all.format.ui;

import javax.swing.*;
import java.awt.*;

public class FlowLayoutDemo {
    public static void main(String[] agrs) {
        JFrame frame = new JFrame("GridLayou布局计算器");
        JPanel panel = new JPanel();//创建面板
        //指定面板的布局为GridLayout，4行4列，间隙为5
        panel.setLayout(new GridLayout(1, 2, 5, 5));
        panel.add(new JButton("7"));//添加按钮
        panel.add(new JButton("8"));
        frame.add(panel);//添加面板到容器
        frame.setBounds(300, 200, 200, 150);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}