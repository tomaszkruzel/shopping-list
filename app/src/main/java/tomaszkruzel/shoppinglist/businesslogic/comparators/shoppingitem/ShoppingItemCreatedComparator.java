package tomaszkruzel.shoppinglist.businesslogic.comparators.shoppingitem;

import tomaszkruzel.shoppinglist.model.ShoppingItem;
import tomaszkruzel.shoppinglist.utils.LongUtils;

import java.util.Comparator;

/**
 * Sorts {@link ShoppingItem}s by creation date in ascending order.
 */
public class ShoppingItemCreatedComparator implements Comparator<ShoppingItem> {

	@Override
	public int compare(final ShoppingItem o1, final ShoppingItem o2) {
		return LongUtils.compare(o1.getCreated(), o2.getCreated());
	}
}
