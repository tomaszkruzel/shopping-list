package tomaszkruzel.shoppinglist;

import com.facebook.stetho.Stetho;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;
import tomaszkruzel.shoppinglist.di.DaggerAppComponent;

public class MyApplication extends DaggerApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
			Stetho.initializeWithDefaults(this);
		}
	}

	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		return DaggerAppComponent.builder()
				.application(this)
				.build();
	}

}
