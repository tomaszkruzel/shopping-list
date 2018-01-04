package tomaszkruzel.shoppinglist.di;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import tomaszkruzel.shoppinglist.MyApplication;

import javax.inject.Singleton;

@Singleton
@Component(modules = { AndroidInjectionModule.class, AppModule.class, MainActivityModule.class })
public interface AppComponent {

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder application(Application application);

		AppComponent build();
	}

	void inject(MyApplication application);
}
