package se.svt.sureshkumar.testapp.util;

import se.svt.sureshkumar.testapp.network.ApiServiceManager;
import se.svt.sureshkumar.testapp.network.volley.VolleyClient;
import se.svt.sureshkumar.testapp.util.image.LruBitmapCache;

import android.app.Application;
import android.content.Context;

import com.android.volley.toolbox.ImageLoader;

public class SvtApplication extends Application {

	public static Context appContext;
	private static ImageLoader imageLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		imageLoader = new ImageLoader(VolleyClient.getInstance(this).getRequestQueue(), new LruBitmapCache(this));
		ApiServiceManager.createInstance(this);
	}

	public static ImageLoader getImageLoader() {
		return imageLoader;
	}
}