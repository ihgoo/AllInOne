package com.ihgoo.allinone.http;

import java.util.HashMap;
import java.util.Map;

import com.ihgoo.allinone.volley.AuthFailureError;
import com.ihgoo.allinone.volley.NetworkResponse;
import com.ihgoo.allinone.volley.Response;
import com.ihgoo.allinone.volley.Response.ErrorListener;
import com.ihgoo.allinone.volley.Response.Listener;
import com.ihgoo.allinone.volley.toolbox.StringRequest;

public class StringRequestCookie extends StringRequest {

	private String mCookie;

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {

		// 仿cookies store, 存储cookies
		HashMap<String, String> map = new HashMap<String, String>();
		for (String s : response.headers.keySet()) {
			if (s.contains("Set-Cookie")) {
				mCookie = response.headers.get(s);
				String[] cookieWhole = mCookie.split("; ");

				for (String string : cookieWhole) {

					int startIndex = string.indexOf("=");
					if (startIndex != -1) {
						String key = string.substring(0, startIndex);
						String value = string.substring(startIndex + 1,
								string.length());
						map.put(key, value);
					}
				}
				
				break;
			}
		}
		return super.parseNetworkResponse(response);
	}

	public StringRequestCookie(String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(url, listener, errorListener);

	}
	
	/**
	 * 重新设置Cookie
	 * @param cookie
	 */
	public void setCookie(String cookie) {
		this.mCookie = cookie;
	}
	
	public void clearCookie(){
		this.mCookie = "";
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> map = new HashMap<String, String>();
//		map.put("Cookie", ApplicationController.cookies);
		return map;
	}

}