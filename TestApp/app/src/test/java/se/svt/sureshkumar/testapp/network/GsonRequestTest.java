package se.svt.sureshkumar.testapp.network;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;

import se.svt.sureshkumar.testapp.config.TestRunner;
import se.svt.sureshkumar.testapp.network.reply.ReplyBase;
import se.svt.sureshkumar.testapp.network.request.SvtRequest;
import se.svt.sureshkumar.testapp.network.volley.GsonRequest;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

@RunWith(TestRunner.class)
public class GsonRequestTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		ApiServiceManager.createInstance(RuntimeEnvironment.application);
	}

	@Test
	public void testGsonRequestClass() {
		SvtRequest svtRequest = Mockito.mock(SvtRequest.class);
		when(svtRequest.getRequestMethodType()).thenReturn(Request.Method.GET);
		when(svtRequest.getBaseUrl()).thenReturn("");

		GsonRequest request = new GsonRequest(svtRequest.getRequestMethodType(), null, svtRequest, ReplyBase.class, null, null, null);
		String json = "{}";
		Map<String, String> headers = new HashMap<String, String>();
		NetworkResponse networkResponse = new NetworkResponse(200, json.getBytes(), headers, false);
		Response<ReplyBase> response = request.parseNetworkResponse(networkResponse);
		assertEquals(200, response.result.responseCode);
	}
}
