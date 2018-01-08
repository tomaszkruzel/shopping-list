package tomaszkruzel.shoppinglist.businesslogic.manager;

import android.support.annotation.NonNull;
import tomaszkruzel.shoppinglist.model.ShoppingItem;

public interface ShoppingItemManager {

	void addShoppingItem(@NonNull String title, final long shoppingListId);

	void editShoppingItemTitle(@NonNull ShoppingItem shoppingItem, @NonNull String title);

	void toggleBought(@NonNull ShoppingItem shoppingItem);

	void remove(@NonNull ShoppingItem shoppingItem);
}
