package com.damoguyansi.all.format.tools;

import com.damoguyansi.all.format.i18n.I18n;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 常用哈希：MD5 / SHA-1 / SHA-256 / SHA-512。
 *
 * @author damoguyansi
 */
public class HashToolPanel extends AbstractToolPanel {

    private final JBTextField hmacKey = new JBTextField(14);

    public HashToolPanel() {
        addButton("MD5", () -> output.setText(hash("MD5")));
        addButton("SHA-1", () -> output.setText(hash("SHA-1")));
        addButton("SHA-256", () -> output.setText(hash("SHA-256")));
        addButton("SHA-512", () -> output.setText(hash("SHA-512")));
        addButton(I18n.message("common.all"), () -> output.setText(
                "MD5     " + hash("MD5") + "\n"
                        + "SHA-1   " + hash("SHA-1") + "\n"
                        + "SHA-256 " + hash("SHA-256") + "\n"
                        + "SHA-512 " + hash("SHA-512")));
        addControl(new JBLabel("  " + I18n.message("hash.hmacKey")));
        addControl(hmacKey);
        addButton("HMAC-SHA256", () -> output.setText(hmac("HmacSHA256")));
    }

    @Override
    public String title() {
        return "Hash";
    }

    private String hmac(String algo) {
        try {
            String key = hmacKey.getText();
            if (key == null || key.isEmpty()) {
                return I18n.message("hash.keyRequired");
            }
            Mac mac = Mac.getInstance(algo);
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algo));
            return toHex(mac.doFinal(inText().getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            return I18n.message("common.error", e.getMessage());
        }
    }

    private String hash(String algo) {
        try {
            byte[] digest = MessageDigest.getInstance(algo)
                    .digest(inText().getBytes(StandardCharsets.UTF_8));
            return toHex(digest);
        } catch (Exception e) {
            return I18n.message("common.error", e.getMessage());
        }
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(Character.forDigit((b >> 4) & 0xF, 16));
            sb.append(Character.forDigit(b & 0xF, 16));
        }
        return sb.toString();
    }
}
