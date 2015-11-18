package se.svt.sureshkumar.testapp.view.fragment;

import se.svt.sureshkumar.testapp.R;
import se.svt.sureshkumar.testapp.model.RadioProgram;
import se.svt.sureshkumar.testapp.util.Constant;
import se.svt.sureshkumar.testapp.util.SvtApplication;
import se.svt.sureshkumar.testapp.view.activity.RadioProgramDetailActivity;
import se.svt.sureshkumar.testapp.view.activity.RadioProgramListActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * A fragment representing a single RadioProgram detail screen. This fragment is either contained in a {@link RadioProgramListActivity} in two-pane mode (on tablets) or a
 * {@link RadioProgramDetailActivity} on handsets.
 */
public class RadioProgramDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the program that this fragment represents.
	 */
	public static final String ARG_ITEM = "program";
	private static final String TAG = RadioProgramDetailFragment.class.getCanonicalName();

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
			setupToolBarTitle();
		}
	}

	private void setupToolBarTitle() {
		try {
			AppCompatActivity activity = (AppCompatActivity) getActivity();
			activity.getSupportActionBar().setTitle(radioProgram.getTitle());
		} catch (Exception ex) {
			if (Constant.DEBUG) Log.d(TAG, ex.toString(), ex);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_radioprogram_detail, container, false);
		if (radioProgram != null) {
			setupView(rootView);
		}
		return rootView;
	}

	private void setupView(View rootView) {
		((TextView) rootView.findViewById(R.id.radioprogramDetailDesc)).setText(radioProgram.getDescription());
		((TextView) rootView.findViewById(R.id.radioprogramDetailTitle)).setText(radioProgram.getTitle());
		setupProgramImage(rootView);
	}

	private void setupProgramImage(View rootView) {
		NetworkImageView programImage = (NetworkImageView) rootView.findViewById(R.id.programImage);
		programImage.setImageUrl(radioProgram.getImageurl(), SvtApplication.getImageLoader());
		programImage.setDefaultImageResId(R.drawable.unknown_program);
		programImage.setErrorImageResId(R.drawable.unknown_program);
	}
}
