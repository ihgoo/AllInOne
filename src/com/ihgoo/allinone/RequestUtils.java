package com.ihgoo.allinone;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.ihgoo.allinone.http.RequestManager;

/**
 * 
 * 初始化请求、获得一个请求队列、将请求添加到请求队列中等方法
 * 
 * @author <a href="http://www.xunhou.me" target="_blank">Kelvin</a>
 *
 */
public class RequestUtils {

	public RequestUtils() {
	}

	public static void init(Context context) {
		RequestManager.init(context);
	}

	public static RequestQueue getRequestQueue() {
		return RequestManager.getRequestQueue();
	}

	public static void addRequsetQueue(Request<?> request, Object tag) {
		RequestManager.addRequest(request, tag);
	}

	public static void addRequstQueue(Request<?> request) {
		RequestManager.addRequest(request, null);
	}

	public static void cancelAll(Object obj) {
		RequestManager.cancelAll(obj);
	}

	public static void cancelAll() {
		RequestManager.cancelAll(null);
	}

	public static ImageLoader getImageLoader() {
		return RequestManager.getImageLoader();
	}

}
