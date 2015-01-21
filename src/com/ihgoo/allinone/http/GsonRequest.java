package com.ihgoo.allinone.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ihgoo.allinone.volley.AuthFailureError;
import com.ihgoo.allinone.volley.NetworkResponse;
import com.ihgoo.allinone.volley.ParseError;
import com.ihgoo.allinone.volley.Request;
import com.ihgoo.allinone.volley.Response;
import com.ihgoo.allinone.volley.Response.ErrorListener;
import com.ihgoo.allinone.volley.Response.Listener;
import com.ihgoo.allinone.volley.toolbox.HttpHeaderParser;

/**
 * Volley adapter for JSON requests with POST method that will be parsed into
 * Java objects by Gson.
 * 
 * @author <a href="http://www.xunhou.me" target="_blank">Kelvin</a>
 * 
 * @param <T>
 */
public class GsonRequest<T> extends Request<T> {
	private Gson mGson = new Gson();
	private Class<T> clazz;
	private Map<String, String> headers;
	private Map<String, String> params;
	private Listener<T> listener;

	/**
	 * Make a GET request and return a parsed object from JSON.
	 *
	 * @param url
	 *            URL of the request to make
	 * @param clazz
	 *            Relevant class object, for Gson's reflection
	 */
	public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.clazz = clazz;
		this.listener = listener;
	}

	/**
	 * Make a POST request and return a parsed object from JSON.
	 *
	 * @param url
	 *            URL of the request to make
	 * @param clazz
	 *            Relevant class object, for Gson's reflection
	 */
	public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {

		super(method, url, errorListener);
		this.clazz = clazz;
		this.params = params;
		this.listener = listener;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers != null ? headers : super.getHeaders();
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			// Misc.log(json +"222");
			return Response.success(mGson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}