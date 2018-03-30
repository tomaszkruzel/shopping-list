package tomaszkruzel.shoppinglist.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import tomaszkruzel.shoppinglist.businesslogic.manager.ShoppingItemManager;
import tomaszkruzel.shoppinglist.db.ShoppingItemDao;
import tomaszkruzel.shoppinglist.model.ShoppingItem;
import tomaszkruzel.shoppinglist.model.ShoppingItemSortingOption;
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.ui.shoppingitems.ShoppingItemsActivity;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShoppingItemsViewModel extends ViewModel {

	private final ShoppingItemManager shoppingItemManager;
	private final LiveData<List<ShoppingItem>> shoppingItems;
	private final MutableLiveData<Comparator<ShoppingItem>> listComparator = new MutableLiveData<>();
	private final long shoppingListId;

	@Inject
	public ShoppingItemsViewModel(final ShoppingItemDao shoppingItemDao,
			final ShoppingItemManager shoppingItemManager,
			@Named(ShoppingItemsActivity.SHOPPING_LIST_KEY) final ShoppingList shoppingList) {
		this.shoppingItemManager = shoppingItemManager;
		listComparator.setValue(ShoppingItemSortingOption.OLDEST_TO_NEWEST.getComparator());
		shoppingListId = shoppingList.getId();
		shoppingItems = Transformations.switchMap(listComparator, value -> {
			return Transformations.map(shoppingItemDao.fetch(shoppingListId), list -> {
				Collections.sort(list, value);
				return list;
			});
		});
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

	public void changeSortingOption(ShoppingItemSortingOption option) {
		listComparator.setValue(option.getComparator());
	}
}
