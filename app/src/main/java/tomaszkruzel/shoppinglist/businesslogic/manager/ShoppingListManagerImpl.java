package tomaszkruzel.shoppinglist.businesslogic.manager;

import android.support.annotation.NonNull;
import tomaszkruzel.shoppinglist.base.AppExecutors;
import tomaszkruzel.shoppinglist.businesslogic.time.TimeProvider;
import tomaszkruzel.shoppinglist.db.ShoppingListDao;
import tomaszkruzel.shoppinglist.model.ShoppingList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ShoppingListManagerImpl implements ShoppingListManager {

	private final ShoppingListDao shoppingListDao;
	private final TimeProvider timeProvider;
	private final AppExecutors appExecutors;

	@Inject
	public ShoppingListManagerImpl(final ShoppingListDao shoppingListDao,
			final TimeProvider timeProvider,
			final AppExecutors appExecutors) {
		this.shoppingListDao = shoppingListDao;
		this.timeProvider = timeProvider;
		this.appExecutors = appExecutors;
	}

	@Override
	public void addShoppingList(@NonNull final String title) {
		appExecutors.diskIO()
				.execute(() -> {
					final ShoppingList shoppingList = new ShoppingList.Builder()//
							.created(timeProvider.getMillisecondSinceUnixEpoch())
							.title(title)
							.archived(false)
							.build();
					shoppingListDao.persist(shoppingList);
				});
	}

	@Override
	public void archive(@NonNull final ShoppingList shoppingList) {
		appExecutors.diskIO()
				.execute(() -> {
					final ShoppingList updatedShoppingList = new ShoppingList.Builder(shoppingList)//
							.archived(true)
							.build();
					shoppingListDao.persist(updatedShoppingList);
				});
	}

	@Override
	public void remove(@NonNull final ShoppingList shoppingList) {
		appExecutors.diskIO()
				.execute(() -> shoppingListDao.remove(shoppingList));
	}
}
