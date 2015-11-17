package se.svt.sureshkumar.testapp.view.adapter;

import java.util.ArrayList;
import java.util.List;

import se.svt.sureshkumar.testapp.R;
import se.svt.sureshkumar.testapp.model.RadioProgram;
import se.svt.sureshkumar.testapp.util.SvtApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

public class ProgramListAdapter extends ArrayAdapter<RadioProgram> {

	private static final String TAG = ProgramListAdapter.class.getSimpleName();
	private final Context context;

	public ProgramListAdapter(Context context, ArrayList<RadioProgram> radioPrograms) {
		super(context, R.layout.radio_program_item, radioPrograms);
		this.context = context;
	}

	public void setAllItems(List<RadioProgram> radioPrograms) {
		if (radioPrograms == null) {
			return;
		}
		clear();
		for (RadioProgram radioProgram : radioPrograms) {
			add(radioProgram);
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.radio_program_item, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.description = (TextView) convertView.findViewById(R.id.description);
			viewHolder.imageView = (NetworkImageView) convertView.findViewById(R.id.programImage);
			convertView.setTag(viewHolder);
			setupNetworkImageView(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final RadioProgram radioProgram = getItem(position);
		viewHolder.title.setText(radioProgram.getTitle());
		viewHolder.description.setText(radioProgram.getDescription());
		viewHolder.imageView.setImageUrl(radioProgram.getImageurl(), SvtApplication.getImageLoader());
		return convertView;
	}

	private void setupNetworkImageView(ViewHolder viewHolder) {
		viewHolder.imageView.setDefaultImageResId(R.drawable.unknown_program);
		viewHolder.imageView.setErrorImageResId(R.drawable.unknown_program);
	}

	static class ViewHolder {
		private TextView title, description;
		private NetworkImageView imageView;
	}
}
