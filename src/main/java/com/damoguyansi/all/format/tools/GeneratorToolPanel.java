package com.damoguyansi.all.format.tools;

import cn.hutool.core.util.IdUtil;
import com.damoguyansi.all.format.i18n.I18n;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 生成器（合并时间戳、UUID/雪花、随机密码）。
 *
 * @author damoguyansi
 */
public class GeneratorToolPanel extends AbstractToolPanel {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String LETTERS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
    private static final String DIGITS = "23456789";
    private static final String SYMBOLS = "!@#$%^&*-_=+";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final JBTextField pwdLen = new JBTextField("16", 3);

    public GeneratorToolPanel() {
        // 时间戳
        addButton(I18n.message("generator.currentTimestamp"), () -> {
            long ms = System.currentTimeMillis();
            output.setText(ms + "  (ms)\n" + (ms / 1000) + "  (s)");
        });
        addButton(I18n.message("generator.timestampToDate"), this::tsToDate);
        addButton(I18n.message("generator.dateToTimestamp"), this::dateToTs);
        // UUID
        addButton("UUID", () -> output.setText(UUID.randomUUID().toString()));
        addButton(I18n.message("generator.uuidNoHyphens"),
                () -> output.setText(UUID.randomUUID().toString().replace("-", "")));
        addButton(I18n.message("generator.snowflakeId"), () -> output.setText(IdUtil.getSnowflakeNextIdStr()));
        // 密码
        addControl(new JBLabel("  " + I18n.message("generator.passwordLength")));
        addControl(pwdLen);
        addButton(I18n.message("generator.randomPassword"), () -> output.setText(password(false)));
        addButton(I18n.message("generator.strongPassword"), () -> output.setText(password(true)));
        addButton(I18n.message("generator.batchPasswords"), () -> output.setText(
                Stream.generate(() -> password(true)).limit(5).collect(Collectors.joining("\n"))));

        input.setText(I18n.message("generator.inputHint"));
    }

    @Override
    public String title() {
        return "Generator";
    }

    private void tsToDate() {
        String digits = inText().replaceAll("\\D", "");
        long value = Long.parseLong(digits);
        long ms = digits.length() <= 10 ? value * 1000 : value;
        Instant instant = Instant.ofEpochMilli(ms);
        output.setText(FMT.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()))
                + "  (" + ZoneId.systemDefault() + ")\n"
                + FMT.format(LocalDateTime.ofInstant(instant, ZoneOffset.UTC)) + "  (UTC)");
    }

    private void dateToTs() {
        long ms = LocalDateTime.parse(inText(), FMT).atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
        output.setText(ms + "  (ms)\n" + (ms / 1000) + "  (s)");
    }

    private String password(boolean withSymbols) {
        int len;
        try {
            len = Math.max(4, Math.min(128, Integer.parseInt(pwdLen.getText().trim())));
        } catch (NumberFormatException e) {
            len = 16;
        }
        String pool = LETTERS + DIGITS + (withSymbols ? SYMBOLS : "");
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(pool.charAt(RANDOM.nextInt(pool.length())));
        }
        return sb.toString();
    }
}
