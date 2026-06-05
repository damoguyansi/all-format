package com.damoguyansi.all.format.tools;

import com.damoguyansi.all.format.util.ClipboardUtil;
import com.damoguyansi.all.format.util.QrCodeCreateUtil;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * 二维码工具（重构版）：上方工具栏输入/操作，中部居中预览，底部识别结果。
 * 支持 Ctrl/Cmd+V 直接粘贴剪贴板图片再识别。
 *
 * @author damoguyansi
 */
public class QrCodeToolPanel extends JPanel implements ToolPanel {

    private static final int QR_SIZE = 280;

    private final JBTextField textField = new JBTextField(26);
    private final JBLabel preview = new JBLabel("", SwingConstants.CENTER);
    private final JBTextArea result = new JBTextArea(2, 10);
    private BufferedImage currentImage;

    public QrCodeToolPanel() {
        super(new BorderLayout(0, 6));
        setBorder(JBUI.Borders.empty(8));

        JPanel bar = ToolUi.toolbar();
        bar.add(new JBLabel("内容："));
        bar.add(textField);
        bar.add(ToolUi.primaryButton("生成", this::generate));
        bar.add(ToolUi.button("粘贴图片", this::pasteImage));
        bar.add(ToolUi.button("识别", this::decode));
        add(bar, BorderLayout.NORTH);

        preview.setVerticalAlignment(SwingConstants.CENTER);
        preview.setText("在此生成二维码，或 Ctrl/Cmd+V 粘贴图片");
        preview.setForeground(JBUI.CurrentTheme.Label.disabledForeground());
        add(new JBScrollPane(preview), BorderLayout.CENTER);

        result.setEditable(false);
        result.setLineWrap(true);
        JBScrollPane resultScroll = new JBScrollPane(result);
        resultScroll.setPreferredSize(new Dimension(10, 56));
        add(resultScroll, BorderLayout.SOUTH);

        // Ctrl/Cmd+V 粘贴图片
        int mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        registerKeyboardAction(e -> pasteImage(),
                KeyStroke.getKeyStroke(KeyEvent.VK_V, mask), WHEN_IN_FOCUSED_WINDOW);
    }

    @Override
    public String title() {
        return "QRCode";
    }

    @Override
    public JComponent component() {
        return this;
    }

    private void generate() {
        String text = textField.getText() == null ? "" : textField.getText().trim();
        if (text.isEmpty()) {
            result.setText("请输入内容");
            return;
        }
        try {
            currentImage = QrCodeCreateUtil.createQrCode(text, QR_SIZE);
            showImage(currentImage);
            result.setText("已生成");
        } catch (Exception e) {
            result.setText("生成失败：" + e.getMessage());
        }
    }

    private void pasteImage() {
        try {
            Image image = ClipboardUtil.getImageFromClipboard();
            if (image == null) {
                result.setText("剪贴板没有图片");
                return;
            }
            currentImage = QrCodeCreateUtil.toBufferedImage(image);
            showImage(currentImage);
            result.setText("已粘贴，点击「识别」解析");
        } catch (Exception e) {
            result.setText("粘贴失败：" + e.getMessage());
        }
    }

    private void decode() {
        if (currentImage == null) {
            result.setText("请先生成或粘贴二维码");
            return;
        }
        try {
            String text = QrCodeCreateUtil.decodeImg(currentImage);
            result.setText(text == null || text.isEmpty() ? "未识别到二维码" : text);
        } catch (Exception e) {
            result.setText("识别失败：" + e.getMessage());
        }
    }

    private void showImage(BufferedImage image) {
        preview.setText("");
        Image scaled = image.getWidth() > QR_SIZE
                ? image.getScaledInstance(QR_SIZE, QR_SIZE, Image.SCALE_SMOOTH) : image;
        preview.setIcon(new ImageIcon(scaled));
    }
}
