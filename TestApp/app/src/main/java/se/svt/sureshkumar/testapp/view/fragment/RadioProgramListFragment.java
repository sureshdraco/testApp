package se.svt.sureshkumar.testapp.view.fragment;

import java.util.ArrayList;

import se.svt.sureshkumar.testapp.R;
import se.svt.sureshkumar.testapp.model.RadioProgram;
import se.svt.sureshkumar.testapp.network.ApiServiceManager;
import se.svt.sureshkumar.testapp.network.reply.ReplyBase;
import se.svt.sureshkumar.testapp.network.reply.TopShowsListReply;
import se.svt.sureshkumar.testapp.network.request.TopListRequest;
import se.svt.sureshkumar.testapp.network.responselisteners.ErrorListener;
import se.svt.sureshkumar.testapp.network.responselisteners.SuccessListener;
import se.svt.sureshkumar.testapp.view.adapter.ProgramListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A list fragment representing a list of RadioPrograms. This fragment also supports tablet devices by allowing list items to be given an 'activated' state upon selection. This
 * helps indicate which item is currently being viewed in a {@link RadioProgramDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks} interface.
 */
public class RadioProgramListFragment extends ListFragment {

	/**
	 * The serialization (saved instance state) Bundle key representing the activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item clicks.
	 */
	private Callbacks mCallbacks = null;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;
	private ProgramListAdapter programListAdapter;

	/**
	 * A callback interface that all activities containing this fragment must implement. This mechanism allows activities to be notified of item selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onProgramSelected(RadioProgram radioProgram);
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation changes).
	 */
	public RadioProgramListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		programListAdapter = new ProgramListAdapter(getContext(), new ArrayList<RadioProgram>());
		setListAdapter(programListAdapter);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getLatestPrograms();
		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	private void getLatestPrograms() {
		ApiServiceManager.getInstance().addRequest(new TopListRequest(TopListRequest.WEEK), TopShowsListReply.class, new SuccessListener<TopShowsListReply>() {

			@Override
			public void onSuccessResponse(TopShowsListReply response) {
				if (getActivity() != null) {
					programListAdapter.setAllItems(response.getShows());
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(ReplyBase response) {
				if (getActivity() != null) {
					Toast.makeText(getActivity(), R.string.failed, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface
		mCallbacks = null;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		mCallbacks.onProgramSelected(programListAdapter.getItem(position));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(activateOnItemClick
				? ListView.CHOICE_MODE_SINGLE
				: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
}
