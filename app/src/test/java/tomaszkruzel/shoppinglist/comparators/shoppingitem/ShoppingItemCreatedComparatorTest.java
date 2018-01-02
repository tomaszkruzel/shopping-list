package tomaszkruzel.shoppinglist.comparators.shoppingitem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tomaszkruzel.shoppinglist.model.ShoppingItem;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static tomaszkruzel.shoppinglist.testutils.DateTestUtil.millisOf;

@RunWith(JUnit4.class)
public class ShoppingItemCreatedComparatorTest {

	private final ShoppingItemCreatedComparator comparator = new ShoppingItemCreatedComparator();
	private final ShoppingItem january = new ShoppingItem.Builder()//
			.created(millisOf(2017, 1, 1))
			.build();

	private final ShoppingItem june = new ShoppingItem.Builder()//
			.created(millisOf(2017, 6, 30))
			.build();

	private final ShoppingItem newYearsEve = new ShoppingItem.Builder()//
			.created(millisOf(2017, 12, 31))
			.build();

	@Test
	public void januaryIsLowerThanJune() {
		asserCorrectOrder(january, june);
	}

	@Test
	public void morningIsLowerThanAfternoon() {
		final ShoppingItem morning = new ShoppingItem.Builder()//
				.created(millisOf(2017, 6, 30, 9, 0))
				.build();
		final ShoppingItem afternoon = new ShoppingItem.Builder()//
				.created(millisOf(2017, 6, 30, 15, 0))
				.build();
		asserCorrectOrder(morning, afternoon);
	}

	@Test
	public void equalDates() {
		final ShoppingItem januaryCopy = new ShoppingItem.Builder(january).build();
		assertThat(comparator.compare(january, januaryCopy), is(0));
	}

	@Test
	public void arrayOrder() {
		final ShoppingItem[] shoppingItems = new ShoppingItem[] { june, newYearsEve, january };
		Arrays.sort(shoppingItems, comparator);
		assertThat(shoppingItems, is(new ShoppingItem[] { january, june, newYearsEve }));
	}

	private void asserCorrectOrder(final ShoppingItem first, final ShoppingItem second) {
		assertThat(comparator.compare(first, second), is(-1));
		assertThat(comparator.compare(second, first), is(1));
	}

}
