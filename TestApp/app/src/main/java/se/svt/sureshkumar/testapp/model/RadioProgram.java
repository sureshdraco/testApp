package se.svt.sureshkumar.testapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by suresh.kumar on 17/11/15.
 */
public class RadioProgram implements Parcelable {
	private String id, title, description, imageurl;

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getImageurl() {
		return imageurl;
	}

	@Override
	public String toString() {
		return "RadioProgram{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", imageurl='" + imageurl + '\'' +
				'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.title);
		dest.writeString(this.description);
		dest.writeString(this.imageurl);
	}

	public RadioProgram() {
	}

	protected RadioProgram(Parcel in) {
		this.id = in.readString();
		this.title = in.readString();
		this.description = in.readString();
		this.imageurl = in.readString();
	}

	public static final Parcelable.Creator<RadioProgram> CREATOR = new Parcelable.Creator<RadioProgram>() {
		public RadioProgram createFromParcel(Parcel source) {
			return new RadioProgram(source);
		}

		public RadioProgram[] newArray(int size) {
			return new RadioProgram[size];
		}
	};
}