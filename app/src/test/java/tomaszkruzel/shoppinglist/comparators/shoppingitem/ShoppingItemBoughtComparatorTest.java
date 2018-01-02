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
public class ShoppingItemBoughtComparatorTest {

	private final ShoppingItemBoughtComparator comparator = new ShoppingItemBoughtComparator();
	private final ShoppingItem mayBought = new ShoppingItem.Builder()//
			.created(millisOf(2017, 5, 26))
			.bought(true)
			.build();

	private final ShoppingItem juneNotBought = new ShoppingItem.Builder()//
			.created(millisOf(2017, 6, 1))
			.bought(false)
			.build();

	@Test
	public void uncompletedIsLowerThanCompleted() {
		asserCorrectOrder(juneNotBought, mayBought);
	}

	@Test
	public void equalDates() {
		final ShoppingItem mayCopy = new ShoppingItem.Builder(mayBought).build();
		assertThat(comparator.compare(mayBought, mayCopy), is(0));
	}

	@Test
	public void arrayOrder() {
		final ShoppingItem[] shoppingItems = new ShoppingItem[] { mayBought, juneNotBought };
		Arrays.sort(shoppingItems, comparator);
		assertThat(shoppingItems, is(new ShoppingItem[] { juneNotBought, mayBought }));
	}

	private void asserCorrectOrder(final ShoppingItem first, final ShoppingItem second) {
		assertThat(comparator.compare(first, second), is(-1));
		assertThat(comparator.compare(second, first), is(1));
	}

}
