package tomaszkruzel.shoppinglist.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tomaszkruzel.shoppinglist.ActiveShoppingListsActivity;

@Module
public abstract class MainActivityModule {

	@ContributesAndroidInjector
	abstract ActiveShoppingListsActivity contributeActiveShoppingListsActivity();
}
