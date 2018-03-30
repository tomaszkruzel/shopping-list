package tomaszkruzel.shoppinglist.di.activity;

import dagger.Binds;
import dagger.Module;
import tomaszkruzel.shoppinglist.ui.BaseDaggerAppCompatActivity;

@Module
public interface BaseDaggerAppCompatActivityModule<A extends BaseDaggerAppCompatActivity> {

	@Binds
	BaseDaggerAppCompatActivity activity(A activity);
}