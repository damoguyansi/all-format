package com.damoguyansi.all.format.tools;

import cn.hutool.core.util.IdUtil;
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
        addButton("当前时间戳", () -> {
            long ms = System.currentTimeMillis();
            output.setText(ms + "  (ms)\n" + (ms / 1000) + "  (s)");
        });
        addButton("时间戳→日期", this::tsToDate);
        addButton("日期→时间戳", this::dateToTs);
        // UUID
        addButton("UUID", () -> output.setText(UUID.randomUUID().toString()));
        addButton("UUID 无横线", () -> output.setText(UUID.randomUUID().toString().replace("-", "")));
        addButton("雪花 ID", () -> output.setText(IdUtil.getSnowflakeNextIdStr()));
        // 密码
        addControl(new JBLabel("  密码长度："));
        addControl(pwdLen);
        addButton("随机密码", () -> output.setText(password(false)));
        addButton("强密码(含符号)", () -> output.setText(password(true)));
        addButton("批量密码×5", () -> output.setText(
                Stream.generate(() -> password(true)).limit(5).collect(Collectors.joining("\n"))));

        input.setText("时间戳转换：在此输入时间戳（秒/毫秒）或日期 yyyy-MM-dd HH:mm:ss");
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
