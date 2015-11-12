package se.svt.sureshkumar.testapp.network.responselisteners;

import java.io.UnsupportedEncodingException;

import se.svt.sureshkumar.testapp.network.reply.ReplyBase;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public abstract class ErrorListener implements Response.ErrorListener {

	private static final String TAG = ErrorListener.class.getCanonicalName();

	@Override
	public void onErrorResponse(final VolleyError error) {
		ReplyBase replyBase = new ReplyBase();
		replyBase = constructErrorReply(error, replyBase);
		onErrorResponse(replyBase);

	}

	private ReplyBase constructErrorReply(VolleyError error, ReplyBase replyBase) {
		if (error.networkResponse == null) {
			// if network failure
			replyBase.responseCode = ReplyBase.NETWORK_ERROR;
		} else {
			// if response from server exists
			try {
				String json = new String(
						error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
				replyBase = new Gson().fromJson(json, ReplyBase.class);
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, e.toString());
			} catch (JsonSyntaxException ex) {
				Log.e(TAG, ex.toString());
			}
			replyBase.responseCode = error.networkResponse.statusCode;
		}

		return replyBase;
	}

	public abstract void onErrorResponse(ReplyBase response);

}