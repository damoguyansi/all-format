package com.damoguyansi.all.format.util;

import java.security.MessageDigest;

/**
 * desc:
 *
 * @author guolong.qu
 * @date 2018/7/20 15:21
 */
public class MD5Util {

	public static String encoderByMd5(String src) throws Throwable {
		MessageDigest digest = MessageDigest.getInstance("md5");
		byte[] result = digest.digest(src.getBytes());
		StringBuffer buffer = new StringBuffer();
		for (byte b : result) {
			int number = b & 0xff;
			String str = Integer.toHexString(number);
			if (str.length() == 1) {
				buffer.append("0");
			}
			buffer.append(str);
		}
		return buffer.toString().toUpperCase();
	}

}
