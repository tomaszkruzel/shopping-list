package tomaszkruzel.shoppinglist.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import tomaszkruzel.shoppinglist.businesslogic.manager.ShoppingListManager;
import tomaszkruzel.shoppinglist.db.ShoppingListDao;
import tomaszkruzel.shoppinglist.model.ShoppingList;

import java.util.List;

public class ActiveShoppingListsViewModel extends ViewModel {

	private final ShoppingListManager shoppingListManager;
	private final LiveData<List<ShoppingList>> activeShoppingLists;

	public ActiveShoppingListsViewModel(final ShoppingListDao shoppingListDao, final ShoppingListManager shoppingListManager) {
		this.shoppingListManager = shoppingListManager;
		activeShoppingLists = shoppingListDao.fetchActive();
	}

	public LiveData<List<ShoppingList>> getActiveShoppingLists() {
		return activeShoppingLists;
	}

	public void addShoppingList(String title) {
		shoppingListManager.addShoppingList(title);
	}

	public void archive(@NonNull ShoppingList shoppingList) {
		shoppingListManager.archive(shoppingList);
	}

	public void remove(@NonNull ShoppingList shoppingList) {
		shoppingListManager.remove(shoppingList);
	}
}
