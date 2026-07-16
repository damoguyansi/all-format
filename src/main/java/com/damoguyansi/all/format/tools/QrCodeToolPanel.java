package com.damoguyansi.all.format.tools;

import com.damoguyansi.all.format.i18n.I18n;
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
        bar.add(new JBLabel(I18n.message("qrcode.content")));
        bar.add(textField);
        bar.add(ToolUi.primaryButton(I18n.message("qrcode.generate"), this::generate));
        bar.add(ToolUi.button(I18n.message("qrcode.pasteImage"), this::pasteImage));
        bar.add(ToolUi.button(I18n.message("qrcode.decode"), this::decode));
        add(bar, BorderLayout.NORTH);

        preview.setVerticalAlignment(SwingConstants.CENTER);
        preview.setText(I18n.message("qrcode.previewHint"));
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
            result.setText(I18n.message("qrcode.enterContent"));
            return;
        }
        try {
            currentImage = QrCodeCreateUtil.createQrCode(text, QR_SIZE);
            showImage(currentImage);
            result.setText(I18n.message("qrcode.generated"));
        } catch (Exception e) {
            result.setText(I18n.message("qrcode.generateFailed", e.getMessage()));
        }
    }

    private void pasteImage() {
        try {
            Image image = ClipboardUtil.getImageFromClipboard();
            if (image == null) {
                result.setText(I18n.message("qrcode.noClipboardImage"));
                return;
            }
            currentImage = QrCodeCreateUtil.toBufferedImage(image);
            showImage(currentImage);
            result.setText(I18n.message("qrcode.pasted"));
        } catch (Exception e) {
            result.setText(I18n.message("qrcode.pasteFailed", e.getMessage()));
        }
    }

    private void decode() {
        if (currentImage == null) {
            result.setText(I18n.message("qrcode.noImage"));
            return;
        }
        try {
            String text = QrCodeCreateUtil.decodeImg(currentImage);
            result.setText(text == null || text.isEmpty() ? I18n.message("qrcode.notFound") : text);
        } catch (Exception e) {
            result.setText(I18n.message("qrcode.decodeFailed", e.getMessage()));
        }
    }

    private void showImage(BufferedImage image) {
        preview.setText("");
        Image scaled = image.getWidth() > QR_SIZE
                ? image.getScaledInstance(QR_SIZE, QR_SIZE, Image.SCALE_SMOOTH) : image;
        preview.setIcon(new ImageIcon(scaled));
    }
}
