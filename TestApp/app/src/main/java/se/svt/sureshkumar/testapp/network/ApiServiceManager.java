package se.svt.sureshkumar.testapp.network;

import se.svt.sureshkumar.testapp.network.reply.ReplyBase;
import se.svt.sureshkumar.testapp.network.request.SVTRequest;
import se.svt.sureshkumar.testapp.network.responselisteners.ErrorListener;
import se.svt.sureshkumar.testapp.network.responselisteners.SuccessListener;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;

public class ApiServiceManager {

	private static ApiServiceManager instance;
	private RequestQueue requestQueue;

	private ApiServiceManager(Context context) {
		this.requestQueue = VolleyClient.getInstance(context).getRequestQueue();
	}

	public static ApiServiceManager getInstance() {
		if (instance == null) {
			throw new IllegalStateException("ApiServiceManager not initialized");
		}
		return instance;
	}

	public static ApiServiceManager createInstance(Context context) {
		instance = new ApiServiceManager(context);
		return instance;
	}

	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	public <T extends ReplyBase> void addRequest(SVTRequest svtRequest, Class<T> replyClass, SuccessListener<T> responseListener, ErrorListener errorListener) {
		addRequest(svtRequest, replyClass, responseListener, errorListener, VolleyClient.NO_RETRY_POLICY, null, false);
	}

	public <T extends ReplyBase> void addRequest(SVTRequest svtRequest, Class<T> replyClass, SuccessListener<T> responseListener, ErrorListener errorListener,
			RetryPolicy retryPolicy, String requestTag, boolean enableClientSideCaching) {
		GsonRequest<T> gsonRequest = new GsonRequest<T>(svtRequest.getRequestMethodType(), svtRequest.getApiUrl(),
				svtRequest, replyClass, svtRequest.getHeaders(), responseListener, errorListener, retryPolicy);
		if (enableClientSideCaching) {
			gsonRequest.cacheResponse();
		}
		if (!TextUtils.isEmpty(requestTag)) {
			gsonRequest.setTag(requestTag);
		}
		requestQueue.add(gsonRequest);
	}
}
