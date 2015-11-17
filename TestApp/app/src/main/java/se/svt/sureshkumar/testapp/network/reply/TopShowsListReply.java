package se.svt.sureshkumar.testapp.network.reply;

import java.util.List;

import se.svt.sureshkumar.testapp.model.RadioProgram;

/**
 * Created by suresh.kumar on 17/11/15.
 */
public class TopShowsListReply extends ReplyBase {

	private List<RadioProgram> shows;

	public List<RadioProgram> getShows() {
		return shows;
	}

	@Override
	public String toString() {
		return "TopShowsListReply{" +
				"shows=" + shows +
				'}';
	}
}
