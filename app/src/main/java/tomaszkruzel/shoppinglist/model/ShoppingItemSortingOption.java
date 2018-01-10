package tomaszkruzel.shoppinglist.model;

import tomaszkruzel.shoppinglist.businesslogic.comparators.MultiComparator;
import tomaszkruzel.shoppinglist.businesslogic.comparators.shoppingitem.ShoppingItemBoughtComparator;
import tomaszkruzel.shoppinglist.businesslogic.comparators.shoppingitem.ShoppingItemCreatedComparator;

import java.util.Collections;
import java.util.Comparator;

public enum ShoppingItemSortingOption {

	OLDEST_TO_NEWEST(new ShoppingItemCreatedComparator()),
	NEWEST_TO_OLDEST(Collections.reverseOrder(new ShoppingItemCreatedComparator())),
	//@formatter:off
	NOT_BOUGHT_FIRST_OLDEST_TO_NEWEST(new MultiComparator<>(
			new ShoppingItemBoughtComparator(),
			new ShoppingItemCreatedComparator())),

	NOT_BOUGHT_FIRST_NEWEST_TO_OLDEST(new MultiComparator<>(
					new ShoppingItemBoughtComparator(),
					Collections.reverseOrder(new ShoppingItemCreatedComparator()))),

	BOUGHT_FIRST_OLDEST_TO_NEWEST(new MultiComparator<>(
			Collections.reverseOrder(new ShoppingItemBoughtComparator()),
			new ShoppingItemCreatedComparator())),

	BOUGHT_FIRST_NEWEST_TO_OLDEST(new MultiComparator<>(
			Collections.reverseOrder(new ShoppingItemBoughtComparator()),
			Collections.reverseOrder(new ShoppingItemCreatedComparator())));
	//@formatter:on

	private final Comparator<ShoppingItem> comparator;

	ShoppingItemSortingOption(final Comparator<ShoppingItem> comparator) {
		this.comparator = comparator;
	}

	public Comparator<ShoppingItem> getComparator() {
		return comparator;
	}
}
