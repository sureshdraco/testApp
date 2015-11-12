package se.svt.sureshkumar.testapp.network.reply;

public class ReplyBase {
	public static final int NETWORK_ERROR = 8001;
	public int responseCode;

	public ReplyBase(int responseCode) {
		this.responseCode = responseCode;
	}

	public ReplyBase() {
	}

	public boolean isOk() {
		return responseCode >= 200 && responseCode < 300;
	}
}
