package tomaszkruzel.shoppinglist.di;

import dagger.Binds;
import dagger.Module;
import tomaszkruzel.shoppinglist.businesslogic.manager.ShoppingListManager;
import tomaszkruzel.shoppinglist.businesslogic.manager.ShoppingListManagerImpl;
import tomaszkruzel.shoppinglist.businesslogic.time.TimeProvider;
import tomaszkruzel.shoppinglist.businesslogic.time.TimeProviderImpl;

@Module
abstract class BusinessLogicModule {

	@Binds
	abstract ShoppingListManager shoppingListManager(ShoppingListManagerImpl shoppingListManager);

	@Binds
	abstract TimeProvider timeProvider(TimeProviderImpl timeProvider);
}
