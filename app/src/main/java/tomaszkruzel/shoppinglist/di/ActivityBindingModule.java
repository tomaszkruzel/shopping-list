package tomaszkruzel.shoppinglist.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tomaszkruzel.shoppinglist.di.activity.shoppingitems.ShoppingItemsModule;
import tomaszkruzel.shoppinglist.ui.activeshoppinglists.ActiveShoppingListsActivity;
import tomaszkruzel.shoppinglist.ui.archivedshoppinglists.ArchivedShoppingListsActivity;
import tomaszkruzel.shoppinglist.ui.shoppingitems.ShoppingItemsActivity;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 */
@Module
public abstract class ActivityBindingModule {

	@ScopeActivity
	@ContributesAndroidInjector
	abstract ActiveShoppingListsActivity activeShoppingListsActivity();

	@ScopeActivity
	@ContributesAndroidInjector
	abstract ArchivedShoppingListsActivity archivedShoppingListsActivity();

	@ScopeActivity
	@ContributesAndroidInjector(modules = ShoppingItemsModule.class)
	abstract ShoppingItemsActivity shoppingItemsActivity();

}
