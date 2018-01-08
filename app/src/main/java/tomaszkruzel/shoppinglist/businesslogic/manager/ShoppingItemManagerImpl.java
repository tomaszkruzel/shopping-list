package tomaszkruzel.shoppinglist.businesslogic.manager;

import android.support.annotation.NonNull;
import tomaszkruzel.shoppinglist.base.AppExecutors;
import tomaszkruzel.shoppinglist.businesslogic.time.TimeProvider;
import tomaszkruzel.shoppinglist.db.ShoppingItemDao;
import tomaszkruzel.shoppinglist.model.ShoppingItem;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ShoppingItemManagerImpl implements ShoppingItemManager {

	private final ShoppingItemDao shoppingItemDao;
	private final TimeProvider timeProvider;
	private final AppExecutors appExecutors;

	@Inject
	public ShoppingItemManagerImpl(final ShoppingItemDao shoppingItemDao,
			final TimeProvider timeProvider,
			final AppExecutors appExecutors) {
		this.shoppingItemDao = shoppingItemDao;
		this.timeProvider = timeProvider;
		this.appExecutors = appExecutors;
	}

	@Override
	public void addShoppingItem(@NonNull final String title, final long shoppingListId) {
		appExecutors.diskIO()
				.execute(() -> {
					final ShoppingItem shoppingItem = new ShoppingItem.Builder()//
							.shoppingListId(shoppingListId)
							.created(timeProvider.getMillisecondSinceUnixEpoch())
							.title(title)
							.bought(false)
							.build();
					shoppingItemDao.insert(shoppingItem);
				});
	}

	@Override
	public void editShoppingItemTitle(@NonNull final ShoppingItem shoppingItem, @NonNull final String title) {
		appExecutors.diskIO()
				.execute(() -> {
					final ShoppingItem updatedShoppingItem = new ShoppingItem.Builder(shoppingItem)//
							.title(title)
							.build();
					shoppingItemDao.insert(updatedShoppingItem);
				});
	}

	@Override
	public void toggleBought(@NonNull final ShoppingItem shoppingItem) {
		appExecutors.diskIO()
				.execute(() -> {
					final ShoppingItem updatedShoppingItem = new ShoppingItem.Builder(shoppingItem)//
							.bought(!shoppingItem.isBought())
							.build();
					shoppingItemDao.insert(updatedShoppingItem);
				});
	}

	@Override
	public void remove(@NonNull final ShoppingItem shoppingItem) {
		appExecutors.diskIO()
				.execute(() -> shoppingItemDao.remove(shoppingItem));
	}
}
