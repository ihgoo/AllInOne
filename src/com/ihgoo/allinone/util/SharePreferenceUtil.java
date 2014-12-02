package com.ihgoo.allinone.util;

import java.util.Set;

import android.R.integer;
import android.R.string;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

/**
 * 
 * @author <a href="http://www.xunhou.me" target="_blank">Kelvin</a>
 *
 */
public class SharePreferenceUtil {

	private static SharedPreferences sp;
	private final static String SharePreferncesName = "SP_SETTING";

	public static void setValue(Context context, String key, Object value) {
		if (sp == null) {
			sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
		}
		Editor edit = sp.edit();
		if (value instanceof String) {
			edit.putString(key, (String) value);
		} else if (value instanceof Boolean) {
			edit.putBoolean(key, (boolean) value);
		} else if (value instanceof Float) {
			edit.putFloat(key, (float) value);
		} else if (value instanceof Integer) {
			edit.putInt(key, (int) value);
		} else if (value instanceof Long) {
			edit.putLong(key, (long) value);
		} else if (value instanceof Set) {
		}
	}

	public static boolean getBoolean(Context context, String key) {
		if (sp == null) {
			sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, false);
	}
	
	public static String getString(Context context ,String key){
		if (sp == null) {
			sp = context.getSharedPreferences(SharePreferncesName, Context.MODE_PRIVATE);
		}
		return sp.getString(key, "");
	}
	

}
