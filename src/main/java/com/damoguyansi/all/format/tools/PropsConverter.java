package com.damoguyansi.all.format.tools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 结构化数据（Map/List）与 .properties 文本互转。
 * <p>嵌套用点号、数组用 {@code [i]}，与 Spring 配置风格一致。
 *
 * @author damoguyansi
 */
final class PropsConverter {

    private static final Pattern INDEX = Pattern.compile("\\[(\\d+)]");

    private PropsConverter() {
    }

    /** 结构化对象 → properties 文本。 */
    static String toProperties(Object root) {
        Map<String, Object> flat = new LinkedHashMap<>();
        flatten("", root, flat);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> e : flat.entrySet()) {
            sb.append(e.getKey()).append('=').append(e.getValue()).append('\n');
        }
        return sb.toString();
    }

    private static void flatten(String prefix, Object node, Map<String, Object> out) {
        if (node instanceof Map) {
            Map<?, ?> m = (Map<?, ?>) node;
            if (m.isEmpty()) {
                out.put(prefix, "");
                return;
            }
            for (Map.Entry<?, ?> e : m.entrySet()) {
                String k = String.valueOf(e.getKey());
                flatten(prefix.isEmpty() ? k : prefix + "." + k, e.getValue(), out);
            }
        } else if (node instanceof List) {
            List<?> l = (List<?>) node;
            if (l.isEmpty()) {
                out.put(prefix, "");
                return;
            }
            for (int i = 0; i < l.size(); i++) {
                flatten(prefix + "[" + i + "]", l.get(i), out);
            }
        } else {
            out.put(prefix, node == null ? "" : node);
        }
    }

    /** properties 文本 → 嵌套结构（Map/List）。 */
    static Object fromProperties(String text) {
        Map<String, Object> root = new LinkedHashMap<>();
        for (String line : text.split("\n")) {
            String t = line.trim();
            if (t.isEmpty() || t.startsWith("#") || t.startsWith("!")) {
                continue;
            }
            int sep = separator(t);
            if (sep < 0) {
                continue;
            }
            put(root, tokens(t.substring(0, sep).trim()), t.substring(sep + 1).trim());
        }
        return root;
    }

    private static int separator(String s) {
        int eq = s.indexOf('=');
        int colon = s.indexOf(':');
        if (eq < 0) return colon;
        if (colon < 0) return eq;
        return Math.min(eq, colon);
    }

    private static List<Object> tokens(String key) {
        List<Object> toks = new ArrayList<>();
        for (String part : key.split("\\.")) {
            int b = part.indexOf('[');
            String name = b < 0 ? part : part.substring(0, b);
            if (!name.isEmpty()) {
                toks.add(name);
            }
            if (b >= 0) {
                Matcher m = INDEX.matcher(part);
                while (m.find()) {
                    toks.add(Integer.parseInt(m.group(1)));
                }
            }
        }
        return toks;
    }

    @SuppressWarnings("unchecked")
    private static void put(Map<String, Object> root, List<Object> toks, String value) {
        Object cur = root;
        for (int i = 0; i < toks.size(); i++) {
            Object tok = toks.get(i);
            boolean last = i == toks.size() - 1;
            Object nextTok = last ? null : toks.get(i + 1);
            if (tok instanceof String) {
                Map<String, Object> map = (Map<String, Object>) cur;
                String k = (String) tok;
                if (last) {
                    map.put(k, coerce(value));
                } else {
                    cur = map.computeIfAbsent(k, x -> nextTok instanceof Integer
                            ? new ArrayList<>() : new LinkedHashMap<>());
                }
            } else {
                List<Object> list = (List<Object>) cur;
                int idx = (Integer) tok;
                while (list.size() <= idx) {
                    list.add(null);
                }
                if (last) {
                    list.set(idx, coerce(value));
                } else {
                    Object child = list.get(idx);
                    if (child == null) {
                        child = nextTok instanceof Integer ? new ArrayList<>() : new LinkedHashMap<>();
                        list.set(idx, child);
                    }
                    cur = child;
                }
            }
        }
    }

    private static Object coerce(String v) {
        if ("true".equalsIgnoreCase(v) || "false".equalsIgnoreCase(v)) {
            return Boolean.parseBoolean(v);
        }
        try {
            return Long.parseLong(v);
        } catch (NumberFormatException ignored) {
        }
        try {
            return Double.parseDouble(v);
        } catch (NumberFormatException ignored) {
        }
        return v;
    }
}
