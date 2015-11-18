package se.svt.sureshkumar.testapp.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.util.SupportFragmentTestUtil;

import se.svt.sureshkumar.testapp.R;
import se.svt.sureshkumar.testapp.config.TestRunner;
import se.svt.sureshkumar.testapp.model.RadioProgram;
import se.svt.sureshkumar.testapp.view.fragment.RadioProgramDetailFragment;

import android.os.Bundle;
import android.widget.TextView;

@RunWith(TestRunner.class)
public class RadioProgramDetailFragmentTest {
	private RadioProgramDetailFragment radioProgramDetailFragment;
	private TextView title, description;

	@Before
	public void setUp() throws Exception {
		radioProgramDetailFragment = new RadioProgramDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(RadioProgramDetailFragment.ARG_ITEM, new RadioProgram("imageurl", "title", "desc"));
		radioProgramDetailFragment.setArguments(bundle);
		SupportFragmentTestUtil.startFragment(radioProgramDetailFragment);
		title = (TextView) radioProgramDetailFragment.getView().findViewById(R.id.radioprogramDetailTitle);
		description = (TextView) radioProgramDetailFragment.getView().findViewById(R.id.radioprogramDetailDesc);
	}

	@Test
	public void testTitle() {
		Assert.assertEquals("title", title.getText().toString());
	}

	@Test
	public void testDetail() {
		Assert.assertEquals("desc", description.getText().toString());
	}
}