package tomaszkruzel.shoppinglist.businesslogic.comparators.shoppinglist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tomaszkruzel.shoppinglist.model.ShoppingList;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static tomaszkruzel.shoppinglist.testutils.DateTestUtil.millisOf;

@RunWith(JUnit4.class)
public class ShoppingListComparatorTest {

	private final ShoppingListComparator comparator = new ShoppingListComparator();
	private final ShoppingList january = new ShoppingList.Builder()//
			.created(millisOf(2017, 1, 1))
			.build();

	private final ShoppingList june = new ShoppingList.Builder()//
			.created(millisOf(2017, 6, 30))
			.build();

	private final ShoppingList newYearsEve = new ShoppingList.Builder()//
			.created(millisOf(2017, 12, 31))
			.build();

	@Test
	public void januaryIsLowerThanJune() {
		asserCorrectOrder(january, june);
	}

	@Test
	public void morningIsLowerThanAfternoon() {
		final ShoppingList morning = new ShoppingList.Builder()//
				.created(millisOf(2017, 6, 30, 9, 0))
				.build();
		final ShoppingList afternoon = new ShoppingList.Builder()//
				.created(millisOf(2017, 6, 30, 15, 0))
				.build();
		asserCorrectOrder(morning, afternoon);
	}

	@Test
	public void equalDates() {
		final ShoppingList januaryCopy = new ShoppingList.Builder(january).build();
		assertThat(comparator.compare(january, januaryCopy), is(0));
	}

	@Test
	public void arrayOrder() {
		final ShoppingList[] shoppingItems = new ShoppingList[] { june, newYearsEve, january };
		Arrays.sort(shoppingItems, comparator);
		assertThat(shoppingItems, is(new ShoppingList[] { january, june, newYearsEve }));
	}

	@Test
	public void reversedComparator() {
		final ShoppingList[] shoppingItems = new ShoppingList[] { june, newYearsEve, january };
		Arrays.sort(shoppingItems, comparator.reversed());
		assertThat(shoppingItems, is(new ShoppingList[] { newYearsEve, june, january }));
	}

	private void asserCorrectOrder(final ShoppingList first, final ShoppingList second) {
		assertThat(comparator.compare(first, second), is(-1));
		assertThat(comparator.compare(second, first), is(1));
	}

}
