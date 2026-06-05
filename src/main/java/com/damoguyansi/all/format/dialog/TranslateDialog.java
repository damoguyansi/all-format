package com.damoguyansi.all.format.dialog;

import com.damoguyansi.all.format.cache.ParamCache;
import com.damoguyansi.all.format.component.HexConvertPanel;
import com.damoguyansi.all.format.constant.Constants;
import com.damoguyansi.all.format.util.ClipboardUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;

public class TranslateDialog extends JFrame {

    // UI Components
    private JPanel centerPanel;
    private JTabbedPane tabbedPane;
    private JButton formatButton;
    private JTextPane qrCodeText;
    private JLabel statusLabel;
    private JTextArea base64Text;
    private JCheckBox alwaysOnTopCheckBox;
    private JButton utilityButton;
    private JCheckBox wrapLinesCheckBox;
    private JTextArea encodeText;
    private JPanel jsonPanel;
//    private JPanel xmlPanel;
    private JPanel htmlPanel;
    private JPanel sqlPanel;
    private JScrollPane qrCodePanel;
    private JScrollPane base64Panel;
    private JScrollPane encodePanel;
    private JLabel sponsorLabel;
    private JTextArea translateInput;
    private JTextArea translateOutput;
    private JScrollPane translateOutputPane;
    private HexConvertPanel hexConvertPanel;
    private JButton urlEncodeButton;
    private JButton urlDecodeButton;
    private JButton md5Button;

    // Syntax Text Areas
    private RSyntaxTextArea jsonText;
    private RSyntaxTextArea xmlText;
    private RSyntaxTextArea htmlText;
    private RSyntaxTextArea sqlText;

    // Dependencies
    public final ParamCache cache;
    private final boolean isDarkTheme;
    private final boolean embedded;
    private final ComponentFactory componentFactory;
    private final ActionHandler actionHandler;

    /** 仅构建面板供 ToolWindow 嵌入（不再有独立窗口）。 */
    public TranslateDialog(boolean isDarkTheme) {
        this.isDarkTheme = isDarkTheme;
        this.embedded = true;
        this.cache = new ParamCache();
        this.componentFactory = new ComponentFactory(this, isDarkTheme);
        this.actionHandler = new ActionHandler(this);

        initializeUI();
        setupEventListeners();
        componentFactory.loadCachedParameters();
        // 工具窗口里「置顶」无意义，隐藏之
        getAlwaysOnTopCheckBox().setVisible(false);
        setClipboardContent();
    }

    public boolean isEmbedded() {
        return embedded;
    }

    private void initializeUI() {
        setContentPane(centerPanel);
        componentFactory.initializeComponents();
    }

    private void setupEventListeners() {
        actionHandler.registerListeners();
    }

    private void setClipboardContent() {
        if (!com.damoguyansi.all.format.settings.AppSettings.getInstance().isSmartClipboard()) {
            return;
        }
        String clipText = ClipboardUtil.getSysClipboardText();
        if (clipText == null || clipText.isEmpty()) {
            return;
        }
        clipText = clipText.trim();
        if (clipText.startsWith("http:") || clipText.startsWith("https:")) {
            selectTab(Constants.ENCODE);
        } else if (clipText.startsWith("{") || clipText.startsWith("[")) {
            selectTab(Constants.JSON);
        }
    }

    private void selectTab(String title) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (title.equals(tabbedPane.getTitleAt(i).trim())) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }
    }

    // Getters for ActionHandler
    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JButton getFormatButton() {
        return formatButton;
    }

    public JButton getUtilityButton() {
        return utilityButton;
    }

    public JTextPane getQrCodeText() {
        return qrCodeText;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public JTextArea getBase64Text() {
        return base64Text;
    }

    public JCheckBox getAlwaysOnTopCheckBox() {
        return alwaysOnTopCheckBox;
    }

    public JCheckBox getWrapLinesCheckBox() {
        return wrapLinesCheckBox;
    }

    public JTextArea getEncodeText() {
        return encodeText;
    }

    public JTextArea getTranslateInput() {
        return translateInput;
    }

    public JTextArea getTranslateOutput() {
        return translateOutput;
    }

    public HexConvertPanel getHexConvertPanel() {
        return hexConvertPanel;
    }

    public JButton getUrlEncodeButton() {
        return urlEncodeButton;
    }

    public JButton getUrlDecodeButton() {
        return urlDecodeButton;
    }

    public JButton getMd5Button() {
        return md5Button;
    }

    public RSyntaxTextArea getJsonText() {
        return jsonText;
    }

    public RSyntaxTextArea getXmlText() {
        return xmlText;
    }

    public RSyntaxTextArea getHtmlText() {
        return htmlText;
    }

    public RSyntaxTextArea getSqlText() {
        return sqlText;
    }

    public String getCodeGithubUrl() {
        return Constants.CODE_GITHUB_URL;
    }

    public JLabel getSponsorLabel() {
        return sponsorLabel;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    public JScrollPane getTranslateOutputPane() {
        return translateOutputPane;
    }

    public JPanel getJsonPanel() {
        return jsonPanel;
    }

    public JPanel getHtmlPanel() {
        return htmlPanel;
    }

    public JPanel getSqlPanel() {
        return sqlPanel;
    }

    public JScrollPane getQrCodePanel() {
        return qrCodePanel;
    }

    public JScrollPane getBase64Panel() {
        return base64Panel;
    }

    public JScrollPane getEncodePanel() {
        return encodePanel;
    }

    // Package-private setters for ComponentFactory
    public void setCenterPanel(JPanel centerPanel) {
        this.centerPanel = centerPanel;
    }

    void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    void setFormatButton(JButton formatButton) {
        this.formatButton = formatButton;
    }

    void setQrCodeText(JTextPane qrCodeText) {
        this.qrCodeText = qrCodeText;
    }

    void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    void setBase64Text(JTextArea base64Text) {
        this.base64Text = base64Text;
    }

    void setAlwaysOnTopCheckBox(JCheckBox alwaysOnTopCheckBox) {
        this.alwaysOnTopCheckBox = alwaysOnTopCheckBox;
    }

    void setUtilityButton(JButton utilityButton) {
        this.utilityButton = utilityButton;
    }

    void setWrapLinesCheckBox(JCheckBox wrapLinesCheckBox) {
        this.wrapLinesCheckBox = wrapLinesCheckBox;
    }

    void setEncodeText(JTextArea encodeText) {
        this.encodeText = encodeText;
    }

    void setJsonPanel(JPanel jsonPanel) {
        this.jsonPanel = jsonPanel;
    }

    void setHtmlPanel(JPanel htmlPanel) {
        this.htmlPanel = htmlPanel;
    }

    void setSqlPanel(JPanel sqlPanel) {
        this.sqlPanel = sqlPanel;
    }

    void setQrCodePanel(JScrollPane qrCodePanel) {
        this.qrCodePanel = qrCodePanel;
    }

    void setBase64Panel(JScrollPane base64Panel) {
        this.base64Panel = base64Panel;
    }

    void setEncodePanel(JScrollPane encodePanel) {
        this.encodePanel = encodePanel;
    }

    void setSponsorLabel(JLabel sponsorLabel) {
        this.sponsorLabel = sponsorLabel;
    }

    void setTranslateInput(JTextArea translateInput) {
        this.translateInput = translateInput;
    }

    void setTranslateOutput(JTextArea translateOutput) {
        this.translateOutput = translateOutput;
    }

    void setTranslateOutputPane(JScrollPane translateOutputPane) {
        this.translateOutputPane = translateOutputPane;
    }

    void setHexConvertPanel(HexConvertPanel hexConvertPanel) {
        this.hexConvertPanel = hexConvertPanel;
    }

    void setUrlEncodeButton(JButton urlEncodeButton) {
        this.urlEncodeButton = urlEncodeButton;
    }

    void setUrlDecodeButton(JButton urlDecodeButton) {
        this.urlDecodeButton = urlDecodeButton;
    }

    void setMd5Button(JButton md5Button) {
        this.md5Button = md5Button;
    }

    void setJsonText(RSyntaxTextArea jsonText) {
        this.jsonText = jsonText;
    }

    void setXmlText(RSyntaxTextArea xmlText) {
        this.xmlText = xmlText;
    }

    void setHtmlText(RSyntaxTextArea htmlText) {
        this.htmlText = htmlText;
    }

    void setSqlText(RSyntaxTextArea sqlText) {
        this.sqlText = sqlText;
    }

}