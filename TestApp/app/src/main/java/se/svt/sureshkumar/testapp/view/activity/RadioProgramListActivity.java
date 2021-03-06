package se.svt.sureshkumar.testapp.view.activity;

import se.svt.sureshkumar.testapp.R;
import se.svt.sureshkumar.testapp.model.RadioProgram;
import se.svt.sureshkumar.testapp.view.fragment.RadioProgramDetailFragment;
import se.svt.sureshkumar.testapp.view.fragment.RadioProgramListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * An activity representing a list of RadioPrograms. This activity has different presentations for handset and tablet-size devices. On handsets, the activity presents a list of
 * items, which when touched, lead to a {@link RadioProgramDetailActivity} representing item details. On tablets, the activity presents the list of items and item details
 * side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a {@link RadioProgramListFragment} and the item details (if present) is a {@link RadioProgramDetailFragment}.
 * <p/>
 * This activity also implements the required {@link RadioProgramListFragment.Callbacks} interface to listen for item selections.
 */
public class RadioProgramListActivity extends AppCompatActivity
		implements RadioProgramListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radioprogram_app_bar);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setTitle(getTitle());

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		if (findViewById(R.id.radioprogram_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-xlarge and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((RadioProgramListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.radioprogram_list))
					.setActivateOnItemClick(true);
		}
	}

	/**
	 * Callback method from {@link RadioProgramListFragment.Callbacks} indicating that the item with the given ID was selected.
	 */
	@Override
	public void onProgramSelected(RadioProgram radioProgram) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putParcelable(RadioProgramDetailFragment.ARG_ITEM, radioProgram);
			RadioProgramDetailFragment fragment = new RadioProgramDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.radioprogram_detail_container, fragment)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, RadioProgramDetailActivity.class);
			detailIntent.putExtra(RadioProgramDetailFragment.ARG_ITEM, radioProgram);
			startActivity(detailIntent);
		}
	}
}
