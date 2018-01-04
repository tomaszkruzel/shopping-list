package tomaszkruzel.shoppinglist;

import android.app.Activity;
import android.app.Application;
import com.facebook.stetho.Stetho;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;
import tomaszkruzel.shoppinglist.di.DaggerAppComponent;

import javax.inject.Inject;

public class MyApplication extends Application implements HasActivityInjector {

	@Inject
	DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

	@Override
	public void onCreate() {
		super.onCreate();
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
			Stetho.initializeWithDefaults(this);
		}
		DaggerAppComponent.builder()
				.application(this)
				.build()
				.inject(this);
	}

	@Override
	public AndroidInjector<Activity> activityInjector() {
		return dispatchingActivityInjector;
	}
}
