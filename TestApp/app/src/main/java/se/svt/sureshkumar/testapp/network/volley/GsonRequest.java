package se.svt.sureshkumar.testapp.network.volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import se.svt.sureshkumar.testapp.network.reply.ReplyBase;
import se.svt.sureshkumar.testapp.util.Constant;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonRequest<T extends ReplyBase> extends Request<T> {
	public static final String HEADER_DATE = "Date";
	public static final String HEADER_E_TAG = "ETag";
	private static final String TAG = "ApiServiceBase"; // All api versions(12, 13 and rapi) have the same tag
	/**
	 * Content type for request.
	 */
	private static final String PROTOCOL_CONTENT_TYPE = "application/json";
	private final Gson gson = new Gson();
	private final Class<T> response;
	private final Listener<T> listener;
	private final ErrorListener errorListener;
	private final Object request;
	private Map<String, String> headers;
	private boolean shouldCacheResponse;

	/**
	 * @param method
	 *            method type of the request (Get, Post)
	 * @param url
	 *            URL of the request to make
	 * @param response
	 *            Relevant class object, for Gson's reflection
	 * @param headers
	 *            Map of request headers
	 */
	public GsonRequest(int method, String url, Object request, Class<T> response, Map<String, String> headers,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.errorListener = errorListener;
		this.response = response;
		this.headers = headers;
		this.listener = listener;
		this.request = request;
	}

	public GsonRequest(int method, String url, Object request, Class<T> response, Map<String, String> headers,
			Listener<T> listener, ErrorListener errorListener, RetryPolicy retryPolicy) {
		this(method, url, request, response, headers, listener, errorListener);
		if (retryPolicy != null) {
			setRetryPolicy(retryPolicy);
		}
	}

	/**
	 * @param method
	 *            method type of the request (Get, Post etc)
	 * @param url
	 *            URL of the request to make
	 * @param response
	 *            Relevant class object, for Gson's reflection
	 */
	public GsonRequest(int method, String url, Object request, Class<T> response,
			Listener<T> listener, ErrorListener errorListener) {
		this(method, url, request, response, null, listener, errorListener);
		setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
	}

	public static Cache.Entry getClientSideCacheHeaders(NetworkResponse response) {
		long now = System.currentTimeMillis();

		Map<String, String> headers = response.headers;
		long serverDate = 0;
		String serverEtag = null;
		String headerValue;

		headerValue = headers.get(HEADER_DATE);
		if (headerValue != null) {
			serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
		}

		serverEtag = headers.get(HEADER_E_TAG);

		final long cacheExpired = (long) 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
		final long softExpire = now;
		final long ttl = now + cacheExpired;

		Cache.Entry entry = new Cache.Entry();
		entry.data = response.data;
		entry.etag = serverEtag;
		entry.softTtl = softExpire;
		entry.ttl = ttl;
		entry.serverDate = serverDate;
		entry.responseHeaders = headers;

		return entry;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		return headers;
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	public Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			if (Constant.DEBUG) Log.d(TAG, "Response[" + "]: " + json);
			T result = gson.fromJson(json, this.response);
			result.responseCode = response.statusCode;
			if (shouldCacheResponse) {
				return Response.success(result, getClientSideCacheHeaders(response));
			} else {
				return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
			}
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		} catch (Exception e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	public String getBodyContentType() {
		return PROTOCOL_CONTENT_TYPE;
	}

	@Override
	public byte[] getBody() {
		String body = "";
		if (request != null) {
			body = gson.toJson(request);
		}
		return body.getBytes();
	}

	public void cacheResponse() {
		shouldCacheResponse = true;
	}

	public Object getRequest() {
		return request;
	}

	public ErrorListener getErrorListener() {
		return errorListener;
	}

	public Class<T> getResponse() {
		return response;
	}

	public Listener<T> getListener() {
		return listener;
	}
}
