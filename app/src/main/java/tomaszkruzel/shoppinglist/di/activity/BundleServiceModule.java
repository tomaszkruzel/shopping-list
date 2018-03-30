package tomaszkruzel.shoppinglist.di.activity;

import dagger.Module;
import dagger.Provides;
import tomaszkruzel.shoppinglist.di.ScopeActivity;
import tomaszkruzel.shoppinglist.ui.BaseDaggerAppCompatActivity;
import tomaszkruzel.shoppinglist.utils.BundleService;

@Module
public class BundleServiceModule {

	@ScopeActivity
	@Provides
	static BundleService provideBundleService(BaseDaggerAppCompatActivity activity) {
		return activity.getBundleService();
	}
}
