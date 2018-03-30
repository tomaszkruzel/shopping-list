package tomaszkruzel.shoppinglist.di;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import tomaszkruzel.shoppinglist.MyApplication;

import javax.inject.Singleton;

@Singleton
@Component(modules = { AndroidInjectionModule.class, AppModule.class, ActivityBindingModule.class, AndroidSupportInjectionModule.class, })
public interface AppComponent extends AndroidInjector<MyApplication> {

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder application(Application application);

		AppComponent build();
	}
}
