package se.svt.sureshkumar.testapp.config;

import java.util.HashMap;

import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;

public class MockNetwork implements Network {

	private String responseJson = "{}";
	private int status = -1;
	private boolean success = true;

	@Override
	public NetworkResponse performRequest(Request<?> request) throws VolleyError {
		if (!success) {
			throw new NetworkError();
		}
		return status == -1 ? new NetworkResponse(responseJson.getBytes()) : new NetworkResponse(status, responseJson.getBytes(), new HashMap<String, String>(), false);
	}

	public void setResponseJson(String responseJson) {
		this.responseJson = responseJson;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}