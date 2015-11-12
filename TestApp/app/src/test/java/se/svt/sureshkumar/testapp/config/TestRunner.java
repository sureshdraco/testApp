package se.svt.sureshkumar.testapp.config;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.FileFsFile;
import org.robolectric.util.Logger;
import org.robolectric.util.ReflectionHelpers;

public class TestRunner extends RobolectricGradleTestRunner {

	public static final String PACKAGE_NAME = "se.svt.sureshkumar.testapp";
	private static final String BUILD_OUTPUT = "build/intermediates";

	public TestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	private static String getType(Config config) {
		try {
			return ReflectionHelpers.getStaticField(config.constants(), "BUILD_TYPE");
		} catch (Throwable e) {
			return null;
		}
	}

	private static String getFlavor(Config config) {
		try {
			return ReflectionHelpers.getStaticField(config.constants(), "FLAVOR");
		} catch (Throwable e) {
			return null;
		}
	}

	private static String getPackageName(Config config) {
		return PACKAGE_NAME;
	}

	@Override
	protected AndroidManifest getAppManifest(Config config) {
		if (config.constants() == Void.class) {
			Logger.error("Field 'constants' not specified in @Config annotation");
			Logger.error("This is required when using TestRunner!");
			throw new RuntimeException("No 'constants' field in @Config annotation!");
		}

		final String type = getType(config);
		final String flavor = getFlavor(config);
		final String packageName = getPackageName(config);

		final FileFsFile res;
		final FileFsFile assets;
		final FileFsFile manifest;

		// res/merged added in Android Gradle plugin 1.3-beta1
		if (FileFsFile.from(BUILD_OUTPUT, "res", "merged").exists()) {
			res = FileFsFile.from(BUILD_OUTPUT, "res", "merged", flavor, type);
		} else if (FileFsFile.from(BUILD_OUTPUT, "res").exists()) {
			res = FileFsFile.from(BUILD_OUTPUT, "res", flavor, type);
		} else {
			res = FileFsFile.from(BUILD_OUTPUT, "bundles", flavor, type, "res");
		}

		if (FileFsFile.from(BUILD_OUTPUT, "assets").exists()) {
			assets = FileFsFile.from(BUILD_OUTPUT, "assets", flavor, type);
		} else {
			assets = FileFsFile.from(BUILD_OUTPUT, "bundles", flavor, type, "assets");
		}

		if (FileFsFile.from(BUILD_OUTPUT, "manifests").exists()) {
			manifest = FileFsFile.from(BUILD_OUTPUT, "manifests", "full", flavor, type, "AndroidManifest.xml");
		} else {
			manifest = FileFsFile.from(BUILD_OUTPUT, "bundles", flavor, type, "AndroidManifest.xml");
		}

		Logger.debug("Robolectric assets directory: " + assets.getPath());
		Logger.debug("   Robolectric res directory: " + res.getPath());
		Logger.debug("   Robolectric manifest path: " + manifest.getPath());
		Logger.debug("    Robolectric package name: " + packageName);
		return new AndroidManifest(manifest, res, assets, packageName);
	}
}