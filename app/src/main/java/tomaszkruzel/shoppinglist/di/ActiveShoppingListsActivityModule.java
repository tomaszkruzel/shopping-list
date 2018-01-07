package tomaszkruzel.shoppinglist.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tomaszkruzel.shoppinglist.ui.activeshoppinglists.ActiveShoppingListsActivity;

@Module
public abstract class ActiveShoppingListsActivityModule {

	@ContributesAndroidInjector
	abstract ActiveShoppingListsActivity contributeActiveShoppingListsActivity();
}
