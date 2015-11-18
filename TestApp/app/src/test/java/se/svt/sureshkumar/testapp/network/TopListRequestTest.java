package se.svt.sureshkumar.testapp.network;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import se.svt.sureshkumar.testapp.config.MockVolleyResponse;
import se.svt.sureshkumar.testapp.network.request.TopListRequest;

/**
 * Created by suresh.kumar on 17/11/15.
 */
public class TopListRequestTest extends TestCase {
	private TopListRequest topListRequest;
	private MockVolleyResponse mockVolleyResponse;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		topListRequest = new TopListRequest(TopListRequest.DAY);
	}

	@Test
	public void testApiUrlWithEmptyQuery() {
		Assert.assertEquals("http://api.sr.se/api/v2/toplist?format=json&interval=1", topListRequest.getApiUrl());
	}
}