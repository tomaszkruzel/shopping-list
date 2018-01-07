package tomaszkruzel.shoppinglist.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import tomaszkruzel.shoppinglist.businesslogic.comparators.shoppinglist.ShoppingListComparator;
import tomaszkruzel.shoppinglist.businesslogic.manager.ShoppingListManager;
import tomaszkruzel.shoppinglist.db.ShoppingListDao;
import tomaszkruzel.shoppinglist.model.ShoppingList;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArchivedShoppingListsViewModel extends ViewModel {

	private final ShoppingListManager shoppingListManager;
	private final LiveData<List<ShoppingList>> archivedShoppingLists;
	private final MutableLiveData<Boolean> fromOldestToLatest = new MutableLiveData<>();
	private ShoppingListComparator ascending = new ShoppingListComparator();
	private Comparator<ShoppingList> descending = Collections.reverseOrder(new ShoppingListComparator());

	@Inject
	public ArchivedShoppingListsViewModel(final ShoppingListDao shoppingListDao, final ShoppingListManager shoppingListManager) {
		this.shoppingListManager = shoppingListManager;
		fromOldestToLatest.setValue(true);
		archivedShoppingLists = Transformations.switchMap(fromOldestToLatest, value -> {
			return Transformations.map(shoppingListDao.fetchArchived(), list -> {
				Collections.sort(list, value ? ascending : descending);
				return list;
			});
		});
	}

	public LiveData<List<ShoppingList>> getArchivedShoppingLists() {
		return archivedShoppingLists;
	}

	public void unarchive(@NonNull ShoppingList shoppingList) {
		shoppingListManager.unarchive(shoppingList);
	}

	public void remove(@NonNull ShoppingList shoppingList) {
		shoppingListManager.remove(shoppingList);
	}

	public void changeSortingOrder() {
		fromOldestToLatest.setValue(!fromOldestToLatest.getValue());
	}
}
