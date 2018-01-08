package tomaszkruzel.shoppinglist.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tomaszkruzel.shoppinglist.ui.shoppingitems.ShoppingItemsActivity;

@Module
public abstract class ShoppingItemsActivityModule {

	@ContributesAndroidInjector
	abstract ShoppingItemsActivity contributeShoppingItemsActivity();
}
