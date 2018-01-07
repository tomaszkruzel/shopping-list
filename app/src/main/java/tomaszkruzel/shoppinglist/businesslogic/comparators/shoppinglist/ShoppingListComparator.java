package tomaszkruzel.shoppinglist.businesslogic.comparators.shoppinglist;

import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.utils.LongUtils;

import java.util.Comparator;

/**
 * Sorts {@link ShoppingList}s by creation date in ascending order.
 */
public class ShoppingListComparator implements Comparator<ShoppingList> {

	@Override
	public int compare(final ShoppingList first, final ShoppingList second) {
		return LongUtils.compare(first.getCreated(), second.getCreated());
	}
}
