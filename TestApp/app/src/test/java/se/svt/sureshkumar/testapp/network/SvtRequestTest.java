package se.svt.sureshkumar.testapp.network;

import java.util.HashMap;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import se.svt.sureshkumar.testapp.config.TestRequest;

/**
 * Created by suresh.kumar on 17/11/15.
 */
public class SvtRequestTest extends TestCase {
	private TestRequest testSvtRequest;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		testSvtRequest = new TestRequest();
	}

	@Test
	public void testApiUrlWithEmptyQuery() {
		testSvtRequest.queryParams = null;
		Assert.assertEquals("www.test.com?format=json", testSvtRequest.getApiUrl());
		testSvtRequest.queryParams = new HashMap<>();
		Assert.assertEquals("www.test.com?format=json", testSvtRequest.getApiUrl());
	}

	@Test
	public void testApiUrlWithTestQuery() throws Exception {
		HashMap<String, String> testQuery = new HashMap<>();
		testQuery.put("key1", "value1");
		testSvtRequest.queryParams = testQuery;
		Assert.assertEquals("www.test.com?format=json&key1=value1", testSvtRequest.getApiUrl());
		testQuery.put("key2", "value2");
		Assert.assertEquals("www.test.com?format=json&key2=value2&key1=value1", testSvtRequest.getApiUrl());
	}
}