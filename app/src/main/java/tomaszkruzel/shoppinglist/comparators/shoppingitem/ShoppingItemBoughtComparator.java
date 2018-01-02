package tomaszkruzel.shoppinglist.comparators.shoppingitem;

import tomaszkruzel.shoppinglist.model.ShoppingItem;
import tomaszkruzel.shoppinglist.utils.BooleanUtils;

import java.util.Comparator;

/**
 * Sorts {@link ShoppingItem}s first not bought then bought.
 */
public class ShoppingItemBoughtComparator implements Comparator<ShoppingItem> {

	@Override
	public int compare(final ShoppingItem o1, final ShoppingItem o2) {
		return BooleanUtils.compare(o1.isBought(), o2.isBought());
	}
}
