package se.svt.sureshkumar.testapp.network.responselisteners;

import se.svt.sureshkumar.testapp.network.reply.ReplyBase;

import com.android.volley.Response;

public abstract class SuccessListener<T extends ReplyBase> implements Response.Listener<T> {
	public abstract void onSuccessResponse(T response);

	@Override
	public void onResponse(T response) {
		onSuccessResponse(response);
	}
}
