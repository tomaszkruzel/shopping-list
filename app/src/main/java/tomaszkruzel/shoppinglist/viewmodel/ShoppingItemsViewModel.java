package tomaszkruzel.shoppinglist.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import tomaszkruzel.shoppinglist.businesslogic.comparators.shoppingitem.ShoppingItemCreatedComparator;
import tomaszkruzel.shoppinglist.businesslogic.manager.ShoppingItemManager;
import tomaszkruzel.shoppinglist.db.ShoppingItemDao;
import tomaszkruzel.shoppinglist.model.ShoppingItem;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShoppingItemsViewModel extends ViewModel {

	private final ShoppingItemManager shoppingItemManager;
	private final LiveData<List<ShoppingItem>> shoppingItems;
	private final MutableLiveData<Boolean> fromOldestToLatest = new MutableLiveData<>();
	private long shoppingListId;
	private ShoppingItemCreatedComparator ascending = new ShoppingItemCreatedComparator();
	private Comparator<ShoppingItem> descending = Collections.reverseOrder(new ShoppingItemCreatedComparator());

	@Inject
	public ShoppingItemsViewModel(final ShoppingItemDao shoppingItemDao, final ShoppingItemManager shoppingItemManager) {
		this.shoppingItemManager = shoppingItemManager;
		fromOldestToLatest.setValue(true);
		shoppingItems = Transformations.switchMap(fromOldestToLatest, value -> {
			return Transformations.map(shoppingItemDao.fetch(shoppingListId), list -> {
				Collections.sort(list, value ? ascending : descending);
				return list;
			});
		});
	}

	public void init(long shoppingListId) {
		this.shoppingListId = shoppingListId;
	}

	public LiveData<List<ShoppingItem>> getShoppingItems() {
		return shoppingItems;
	}

	public void addShoppingItem(String title) {
		shoppingItemManager.addShoppingItem(title, shoppingListId);
	}

	public void editShoppingItemTitle(ShoppingItem shoppingItem, String title) {
		shoppingItemManager.editShoppingItemTitle(shoppingItem, title);
	}

	public void toggleBought(ShoppingItem shoppingItem) {
		shoppingItemManager.toggleBought(shoppingItem);
	}

	public void remove(@NonNull ShoppingItem shoppingItem) {
		shoppingItemManager.remove(shoppingItem);
	}

	public void changeSortingOrder() {
		fromOldestToLatest.setValue(!fromOldestToLatest.getValue());
	}
}
