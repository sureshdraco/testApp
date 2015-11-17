package se.svt.sureshkumar.testapp.view.fragment;

import se.svt.sureshkumar.testapp.R;
import se.svt.sureshkumar.testapp.model.RadioProgram;
import se.svt.sureshkumar.testapp.view.activity.RadioProgramDetailActivity;
import se.svt.sureshkumar.testapp.view.activity.RadioProgramListActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single RadioProgram detail screen. This fragment is either contained in a {@link RadioProgramListActivity} in two-pane mode (on tablets) or a
 * {@link RadioProgramDetailActivity} on handsets.
 */
public class RadioProgramDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the program that this fragment represents.
	 */
	public static final String ARG_ITEM = "program";

	private RadioProgram radioProgram;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation changes).
	 */
	public RadioProgramDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM)) {
			radioProgram = getArguments().getParcelable(ARG_ITEM);
			Activity activity = this.getActivity();
			CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
			if (appBarLayout != null) {
				appBarLayout.setTitle(radioProgram.getTitle());
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_radioprogram_detail, container, false);

		if (radioProgram != null) {
			((TextView) rootView.findViewById(R.id.radioprogram_detail)).setText(radioProgram.getDescription());
		}

		return rootView;
	}
}
