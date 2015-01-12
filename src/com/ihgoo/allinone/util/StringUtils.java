package com.ihgoo.allinone.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.TextUtils;

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

		int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0]
				.length()) + sepSize) * arraySize);
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

	/**
	 * 将长字符从截取剩下的用...代替
	 * 
	 * @param input
	 * @param count
	 * @return
	 */
	public static String cutString(String input, int count) {
		return cutString(input, count, null);
	}

	/**
	 * 将长字符从截取剩下的用more代替,more为空则用省略号代替
	 * 
	 * @param input
	 * @param count
	 * @param more
	 * @return
	 */
	public static String cutString(String input, int count, String more) {
		String resultString = "";
		if (input != null) {
			if (more == null) {
				more = "...";
			}
			if (input.length() > count) {
				resultString = input.substring(0, count) + more;
			} else {
				resultString = input;
			}
		}
		return resultString;
	}
	
	/**
	 * 获得指定中文长度对应的字符串长度，用于截取显示文字，一个中文等于两个英文
	 * 
	 * @param chineseLengthForDisplay
	 * @param content
	 * @return
	 */
	public static int chineseWidth2StringLenth(int chineseLengthForDisplay, String string) {
		int result = 0;
		int displayWidth = chineseLengthForDisplay * 2;
		if (string != null) {
			for (char chr : string.toCharArray()) {
				// 中文
				if (chr >= 0x4e00 && chr <= 0x9fbb) {
					displayWidth -= 2;
				} else {
					// 英文
					displayWidth -= 1;
				}
				if (displayWidth <= 0) {
					break;
				}
				result++;
			}
		}
		return result;
	}
	
	/**
	 * 将长时间格式字符串转换为字符串,默认为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 *            long型时间,支持毫秒和秒
	 * 
	 * @param dataFormat
	 *            需要返回的时间格式，例如： yyyy-MM-dd， yyyy-MM-dd HH:mm:ss
	 * 
	 * @return dataFormat格式的时间结果字符串
	 */
	public static String dateFormat(long milliseconds, String dataFormat) {
		long tempTimestamp = milliseconds > 9999999999L ? milliseconds : milliseconds *1000;
		if (TextUtils.isEmpty(dataFormat)) {
			dataFormat = "yyyy-MM-dd HH:mm:ss";
		}
		Date date = new Date(tempTimestamp * 1l);
		SimpleDateFormat formatter = new SimpleDateFormat(dataFormat);
		return formatter.format(date);
	}
}
