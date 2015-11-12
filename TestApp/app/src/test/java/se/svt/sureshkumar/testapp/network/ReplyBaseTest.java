package se.svt.sureshkumar.testapp.network;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import se.svt.sureshkumar.testapp.network.reply.ReplyBase;

public class ReplyBaseTest extends TestCase {

	@Test
	public void testIsOk() {
		ReplyBase replyBase = new ReplyBase(200);
		Assert.assertTrue(replyBase.isOk());
		replyBase.responseCode = 204;
		Assert.assertTrue(replyBase.isOk());
	}

	@Test
	public void testIsNotOk() {
		ReplyBase replyBase = new ReplyBase(300);
		Assert.assertFalse(replyBase.isOk());
	}
}
