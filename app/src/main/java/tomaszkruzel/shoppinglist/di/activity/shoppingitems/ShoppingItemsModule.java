package tomaszkruzel.shoppinglist.di.activity.shoppingitems;

import dagger.Module;
import dagger.Provides;
import tomaszkruzel.shoppinglist.di.ScopeActivity;
import tomaszkruzel.shoppinglist.di.activity.BaseDaggerAppCompatActivityModule;
import tomaszkruzel.shoppinglist.di.activity.BundleServiceModule;
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.ui.shoppingitems.ShoppingItemsActivity;
import tomaszkruzel.shoppinglist.utils.BundleService;

import javax.inject.Named;

@Module(includes = BundleServiceModule.class)
public abstract class ShoppingItemsModule implements BaseDaggerAppCompatActivityModule<ShoppingItemsActivity> {

	@ScopeActivity
	@Provides
	@Named(ShoppingItemsActivity.SHOPPING_LIST_KEY)
	static ShoppingList provideShoppingList(BundleService bundleService) {
		return (ShoppingList) bundleService.get(ShoppingItemsActivity.SHOPPING_LIST_KEY);
	}
}
