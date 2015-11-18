package se.svt.sureshkumar.testapp.config;

import java.io.File;
import java.util.concurrent.Executors;

import org.robolectric.RuntimeEnvironment;

import se.svt.sureshkumar.testapp.network.ApiServiceManager;
import se.svt.sureshkumar.testapp.network.volley.VolleyClient;

import android.content.Context;

import com.android.volley.ExecutorDelivery;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.DiskBasedCache;

public class MockVolleyResponse {

	private MockNetwork network;

	public MockVolleyResponse() {
		setupMockedNetwork();
		ApiServiceManager.createInstance(RuntimeEnvironment.application);
	}

	public static void waitForDummyResponse() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void expectSuccess(String responseJson) {
		network.setSuccess(true);
		network.setResponseJson(responseJson);
	}

	public void expectFailure() {
		network.setSuccess(false);
	}

	private void setupMockedNetwork() {
		RequestQueue requestQueue = newRequestQueueForTest(RuntimeEnvironment.application);
		VolleyClient.getInstance(RuntimeEnvironment.application).setRequestQueue(requestQueue);
	}

	private RequestQueue newRequestQueueForTest(final Context context) {
		final File cacheDir = new File(context.getCacheDir(), "volley");
		network = new MockNetwork();
		final ResponseDelivery responseDelivery = new ExecutorDelivery(Executors.newSingleThreadExecutor());
		final RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network, 4, responseDelivery);
		queue.start();
		return queue;
	}
}
