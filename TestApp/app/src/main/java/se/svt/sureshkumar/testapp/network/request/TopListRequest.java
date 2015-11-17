package se.svt.sureshkumar.testapp.network.request;

import java.util.HashMap;

import com.android.volley.Request;

/**
 * Created by suresh.kumar on 17/11/15.
 */
public class TopListRequest extends SvtRequest {

	public static final String TOP_LIST_API_URL = "http://api.sr.se/api/v2/toplist";
	public static final int DAY = 1;
	public static final int WEEK = 7;
	public static final int MONTH = 30;
	public static final String INTERVAL = "interval";

	private int interval;

	public TopListRequest(int interval) {
		this.interval = interval;
	}

	@Override
	public int getRequestMethodType() {
		return Request.Method.GET;
	}

	@Override
	public String getBaseUrl() {
		return TOP_LIST_API_URL;
	}

	@Override
	public HashMap<String, String> getQueryParams() {
		HashMap<String, String> queryParams = new HashMap<>();
		queryParams.put(INTERVAL, String.valueOf(interval));
		return queryParams;
	}
}
