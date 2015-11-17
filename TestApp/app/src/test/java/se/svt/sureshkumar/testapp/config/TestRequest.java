package se.svt.sureshkumar.testapp.config;

import java.util.HashMap;

import se.svt.sureshkumar.testapp.network.request.SvtRequest;

/**
 * Created by suresh.kumar on 17/11/15.
 */
public class TestRequest extends SvtRequest {

	public HashMap<String, String> queryParams;

	@Override
	public int getRequestMethodType() {
		return 0;
	}

	@Override
	public String getBaseUrl() {
		return "www.test.com";
	}

	@Override
	public HashMap<String, String> getQueryParams() {
		return queryParams;
	}
}
