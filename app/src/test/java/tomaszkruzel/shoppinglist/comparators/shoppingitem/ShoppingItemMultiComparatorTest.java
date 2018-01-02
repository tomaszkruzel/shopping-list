package tomaszkruzel.shoppinglist.comparators.shoppingitem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tomaszkruzel.shoppinglist.comparators.MultiComparator;
import tomaszkruzel.shoppinglist.model.ShoppingItem;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static tomaszkruzel.shoppinglist.testutils.DateTestUtil.millisOf;

@RunWith(JUnit4.class)
public class ShoppingItemMultiComparatorTest {

	private final ShoppingItem mayBought = new ShoppingItem.Builder()//
			.created(millisOf(2017, 5, 29))
			.bought(true)
			.build();

	private final ShoppingItem mayNotBought = new ShoppingItem.Builder(mayBought)//
			.bought(false)
			.build();

	private final ShoppingItem juneBought = new ShoppingItem.Builder()//
			.created(millisOf(2017, 6, 29))
			.bought(true)
			.build();

	private final ShoppingItem juneNotBought = new ShoppingItem.Builder(juneBought)//
			.bought(false)
			.build();

	private final ShoppingItem decemberBought = new ShoppingItem.Builder()//
			.created(millisOf(2017, 12, 31))
			.bought(true)
			.build();

	private final ShoppingItem decemberNotBought = new ShoppingItem.Builder(decemberBought)//
			.bought(false)
			.build();

	private ShoppingItem[] shoppingItems;

	@Before
	public void setup() {
		shoppingItems = new ShoppingItem[] { mayBought, mayNotBought, juneBought, juneNotBought, decemberBought, decemberNotBought };
	}

	@Test
	public void sortingByBoughtAndCreated() {
		Arrays.sort(shoppingItems, new MultiComparator<>(new ShoppingItemBoughtComparator(), new ShoppingItemCreatedComparator()));
		assertThat(shoppingItems,
				is(new ShoppingItem[] { mayNotBought, juneNotBought, decemberNotBought, mayBought, juneBought, decemberBought }));
	}

	@Test
	public void sortingByCreatedAndBought() {
		Arrays.sort(shoppingItems, new MultiComparator<>(new ShoppingItemCreatedComparator(), new ShoppingItemBoughtComparator()));
		assertThat(shoppingItems,
				is(new ShoppingItem[] { mayNotBought, mayBought, juneNotBought, juneBought, decemberNotBought, decemberBought }));
	}

	@Test
	public void sortingByCreatedDescAndBought() {
		Arrays.sort(shoppingItems,
				new MultiComparator<>(new ShoppingItemCreatedComparator().reversed(), new ShoppingItemBoughtComparator()));
		assertThat(shoppingItems,
				is(new ShoppingItem[] { decemberNotBought, decemberBought, juneNotBought, juneBought, mayNotBought, mayBought }));
	}

	@Test
	public void sortingByCreatedDescAndBoughtDesc() {
		Arrays.sort(shoppingItems,
				new MultiComparator<>(new ShoppingItemCreatedComparator().reversed(), new ShoppingItemBoughtComparator().reversed()));
		assertThat(shoppingItems,
				is(new ShoppingItem[] { decemberBought, decemberNotBought, juneBought, juneNotBought, mayBought, mayNotBought }));
	}

	@Test
	public void sortingByBoughtDescAndCreated() {
		Arrays.sort(shoppingItems,
				new MultiComparator<>(new ShoppingItemBoughtComparator().reversed(), new ShoppingItemCreatedComparator()));
		assertThat(shoppingItems,
				is(new ShoppingItem[] { mayBought, juneBought, decemberBought, mayNotBought, juneNotBought, decemberNotBought }));
	}

}
