package se.svt.sureshkumar.testapp.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.util.SupportFragmentTestUtil;

import se.svt.sureshkumar.testapp.config.MockVolleyResponse;
import se.svt.sureshkumar.testapp.config.TestRunner;
import se.svt.sureshkumar.testapp.view.fragment.RadioProgramListFragment;

import android.view.View;
import android.widget.ListView;

@RunWith(TestRunner.class)
public class RadioProgramListFragmentTest {
	private RadioProgramListFragment radioProgramListFragment;
	private ListView listView;
	private MockVolleyResponse mockVolleyResponse;

	@Before
	public void setUp() throws Exception {
		mockVolleyResponse = new MockVolleyResponse();
		radioProgramListFragment = new RadioProgramListFragment();
		SupportFragmentTestUtil.startFragment(radioProgramListFragment);
		listView = (ListView) radioProgramListFragment.getView().findViewById(android.R.id.list);
	}

	@Test
	public void testListItemCountWhenApiSuccess() {
		mockVolleyResponse.expectSuccess("{  \n" +
				"   \"shows\":[  \n" +
				"      {  \n" +
				"         \"id\":5518984,\n" +
				"         \"title\":\"P4 Melodikrysset  20151114 1003\",\n" +
				"         \"description\":\"Ett musikaliskt korsord med Anders Eldeman.\",\n" +
				"         \"imageurl\":\"http://sverigesradio.se/sida/images/2078/3286437_1400_1400.jpg?preset=api-default-square\"\n" +
				"      }, \n" +
				"{  \n" +
				"         \"id\":5518985,\n" +
				"         \"title\":\"P5\",\n" +
				"         \"description\":\"med Anders Eldeman.\",\n" +
				"         \"imageurl\":\"http://sverigesradio.se/sida/images/2078/3286437_1400_1400.jpg?preset=api-default-square\"\n" +
				"      } ]}");
		MockVolleyResponse.waitForDummyResponse();
		Assert.assertEquals(2, listView.getAdapter().getCount());
		Assert.assertEquals(View.GONE, listView.getEmptyView().getVisibility());
	}

	@Test
	public void testListItemCountWhenApiFail() {
		mockVolleyResponse.expectFailure();
		MockVolleyResponse.waitForDummyResponse();
		Assert.assertEquals(0, listView.getAdapter().getCount());
		Assert.assertEquals(View.VISIBLE, listView.getEmptyView().getVisibility());
	}
}