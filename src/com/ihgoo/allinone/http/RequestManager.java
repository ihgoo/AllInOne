package com.ihgoo.allinone.http;

import android.app.ActivityManager;
import android.content.Context;

import com.ihgoo.allinone.volley.Request;
import com.ihgoo.allinone.volley.RequestQueue;
import com.ihgoo.allinone.volley.toolbox.ImageLoader;
import com.ihgoo.allinone.volley.toolbox.Volley;

/**
 * Init Volley method and provide instance of requestqueue...
 * 
 * @author <a href="http://www.xunhou.me" target="_blank">Kelvin</a>
 *
 */
public class RequestManager {
	private static RequestQueue mRequestQueue;

	private RequestManager() {
		// no instances
	}

	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		// Use 1/8th of the available memory for this memory cache.
		int cacheSize = 1024 * 1024 * memClass / 8;
	}
	
	public static void getBitmap(String url){
//		mImageLoader.getImageListener(view, defaultImageResId, errorImageResId)
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	public static void addRequest(Request<?> request, Object tag) {
		if (tag != null) {
			request.setTag(tag);
		}
		mRequestQueue.add(request);
	}

	public static void cancelAll(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

}
