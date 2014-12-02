package com.ihgoo.allinone.util;

import android.annotation.SuppressLint;

public final class StringUtils {

	/**
	 * 判断字符串是否为null或""
	 * 
	 * @param string
	 * @return 为空或null返回false，否则返回true
	 */
	@SuppressLint("NewApi")
	public static boolean isNull(String string) {
		if (string == null && "".equals(string)) {
			return false;
		}
		return true;
	}

	public static String join(String[] array, String sep) {
		if (array == null) {
			return null;
		}

		int arraySize = array.length;
		int sepSize = 0;
		if (sep != null && !sep.equals("")) {
			sepSize = sep.length();
		}

		int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].length()) + sepSize) * arraySize);
		StringBuilder buf = new StringBuilder(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(sep);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	// no null in array
	public static String jsonJoin(String[] array) {
		int arraySize = array.length;
		int bufSize = arraySize * (array[0].length() + 3);
		StringBuilder buf = new StringBuilder(bufSize);
		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(',');
			}

			buf.append('"');
			buf.append(array[i]);
			buf.append('"');
		}
		return buf.toString();
	}
}
