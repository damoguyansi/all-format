package com.damoguyansi.all.format.util;

import java.math.BigInteger;

/**
 * 数字进制相互转换
 */
public class NumberUtil {
    /**
     * 十进制转二进制
     *
     * @param number
     * @return
     */
    public static String toBinaryString(int number) {
        return Integer.toBinaryString(number);
    }

    /**
     * 十进制转八进制
     *
     * @param number
     * @return
     */
    public static String toOctalString(int number) {
        return Integer.toOctalString(number);
    }

    /**
     * 十进制转十六进制
     *
     * @param number
     * @return
     */
    public static String toHexString(int number) {
        return Integer.toHexString(number);
    }

    /**
     * 十进制转三十二进制
     *
     * @param number
     * @return
     */
    public static String toTTString(int number) {
        return new java.math.BigInteger(String.valueOf(number), 10).toString(32);
    }

    /**
     * 其他进制转十进制
     *
     * @param number
     * @return
     */
    public static int scale2Decimal(String number, int scale) {
        if (2 > scale || scale > 32) {
            throw new IllegalArgumentException("scale is not in range");
        }
        String str = new BigInteger(number, scale).toString(10);
        return Integer.parseInt(str);
    }

    public static void main(String[] args) {
        System.out.println(scale2Decimal("w",32));
    }
}