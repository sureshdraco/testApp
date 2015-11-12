package se.svt.sureshkumar.testapp.network.request;

import java.util.Map;
import java.util.TreeMap;

import com.android.volley.Request;
import com.google.gson.Gson;

public abstract class SVTRequest {

	protected transient TreeMap<String, String> requestHeader = new TreeMap<String, String>();

	public abstract int getRequestMethodType();

	public abstract String getApiUrl();

	public Map<String, String> getHeaders() {
		return requestHeader;
	}

	public String getJsonBody() {
		switch (getRequestMethodType()) {
		case Request.Method.DELETE:
		case Request.Method.GET:
			return "";
		case Request.Method.POST:
		case Request.Method.PUT:
		case Request.Method.PATCH:
		default:
			return new Gson().toJson(this);
		}
	}
}
