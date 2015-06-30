package com.ihgoo.allinone.support;

import java.lang.reflect.Field;

import org.json.JSONObject;

public class ParseUtil {
	 public static boolean isInt(Field field) {
	        return field.getType().getSimpleName().equals("int");
	    }

	    public static int getInt(Object object, int defaultValue) {

	        String str = "";
	        if (object != null) {
	            str = object.toString();

	        }
	        return parseInt(str, defaultValue);
	    }

	    public static long getLong(Object object, long defaultValue) {
	        String str = "";
	        if (object != null) {
	            str = object.toString();

	        }
	        return parseLong(str, defaultValue);
	    }

	    public static long getLong(JSONObject jsonObject, String name, long defaultValue) {
	        long result = defaultValue;
	        try {
	            if (jsonObject != null && jsonObject.has(name)) {
	                result = jsonObject.getLong(name);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	    }

	    public static int parseInt(String object, int defalult) {
	        return (int) parseDouble(object, defalult);
	    }

	    public static float parseFloat(String object, float defalult) {
	        return (float) parseDouble(object, defalult);
	    }

	    public static long parseLong(String object, long defalult) {
	        return (long) parseDouble(object, defalult);
	    }

	    public static double parseDouble(String object, double defalult) {
	        double result = defalult;
	        if (object != null && object.length() > 0) {
	            try {
	                result = Double.parseDouble(object);
	            } catch (Exception e) {

	            }
	        }
	        return result;
	    }

	    public static int getInt(JSONObject jsonObject, String name, int defaultValue) {
	        int result = defaultValue;
	        try {
	            if (jsonObject != null && jsonObject.has(name)) {
	                result = jsonObject.getInt(name);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;

	    }
}
