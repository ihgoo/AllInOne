package com.ihgoo.allinone.util;

/**
 * Created by ihgoo on 2015/6/16.
 */
public class CommUtil {
	
	private static long lastClickTime;

	/**
	 * 是否快速点击
	 * 
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

}
