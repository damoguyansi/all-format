package com.damoguyansi.all.format.util;

import com.intellij.notification.*;
import com.intellij.openapi.ui.Messages;

import java.io.PrintWriter;
import java.io.StringWriter;

public class NoticeUtil {
    private static String NAME;

    private static int LEVEL;

    private static final int INFO = 1;

    private static final int ERROR = 2;

    static {
        LEVEL = 1;
    }

    /**
     * 初始化注册日志函数
     *
     * @param name
     * @param level
     */
    public static void init(final String name, final int level) {
        NoticeUtil.NAME = name;
        NoticeUtil.LEVEL = level;
        NotificationsConfiguration.getNotificationsConfiguration().register(NoticeUtil.NAME, NotificationDisplayType.NONE);
    }

    /**
     * 正常显示
     *
     * @param opeName
     * @param text
     */
    public static void info(final String opeName, final String text) {
        if (NoticeUtil.LEVEL == 1) {
            Notifications.Bus.notify(new Notification(NoticeUtil.NAME, NoticeUtil.NAME + (opeName.equals(TranslateUtil.ZH_CN_TO_EN) ? " 中译英 " : " 英译中 ") + " [INFO]", text, NotificationType.INFORMATION));
        }
    }

    /**
     * 错误信息
     *
     * @param text
     */
    public static void error(final String text) {
        Messages.showMessageDialog(
                text,
                "错误",
                Messages.getInformationIcon()
        );
    }

    /**
     * 错误信息
     *
     * @param throwable
     */
    public static void error(Throwable throwable) {
        Messages.showMessageDialog(
                getStackTrace(throwable),
                "错误",
                Messages.getInformationIcon()
        );
    }

    /**
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
