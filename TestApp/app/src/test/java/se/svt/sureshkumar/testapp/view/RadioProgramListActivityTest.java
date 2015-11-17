package se.svt.sureshkumar.testapp.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import se.svt.sureshkumar.testapp.BuildConfig;
import se.svt.sureshkumar.testapp.R;
import se.svt.sureshkumar.testapp.config.TestRunner;
import se.svt.sureshkumar.testapp.view.activity.RadioProgramListActivity;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(TestRunner.class)
@Config(emulateSdk = 18, constants = BuildConfig.class)
public class RadioProgramListActivityTest {

	private ActivityController<RadioProgramListActivity> activityController;

	@Before
	public void setUp() throws Exception {
		activityController = Robolectric.buildActivity(RadioProgramListActivity.class);
	}

	@Test
	@Config(emulateSdk = 18, constants = BuildConfig.class, qualifiers = "xlarge")
	public void testLayoutIsTwoPaneForLargeScreens() throws Exception {
		RadioProgramListActivity radioProgramListActivity = getRadioProgramListActivity();
		Assert.assertNotNull(radioProgramListActivity.findViewById(R.id.radioprogram_detail_container));
	}

	private RadioProgramListActivity getRadioProgramListActivity() {
		return activityController.create()
				.start()
				.resume()
				.visible().get();
	}

	@Test
	@Config(emulateSdk = 18, constants = BuildConfig.class, qualifiers = "normal")
	public void testLayoutIsOnePaneForNormalScreens() throws Exception {
		RadioProgramListActivity radioProgramListActivity = getRadioProgramListActivity();
		Assert.assertNull(radioProgramListActivity.findViewById(R.id.radioprogram_detail_container));
	}
}