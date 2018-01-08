package tomaszkruzel.shoppinglist.businesslogic.manager;

import android.support.annotation.NonNull;
import tomaszkruzel.shoppinglist.model.ShoppingList;

public interface ShoppingListManager {

	void addShoppingList(@NonNull String title);

	void archive(@NonNull ShoppingList shoppingList);

	void remove(@NonNull ShoppingList shoppingList);

	void unarchive(ShoppingList shoppingList);

	void editShoppingItemTitle(ShoppingList shoppingList, String title);
}
