package tomaszkruzel.shoppinglist.db;

import android.database.sqlite.SQLiteConstraintException;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tomaszkruzel.shoppinglist.model.ShoppingItem;
import tomaszkruzel.shoppinglist.model.ShoppingList;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static tomaszkruzel.shoppinglist.testutils.DateTestUtil.millisOf;
import static tomaszkruzel.shoppinglist.testutils.LiveDataTestUtil.getValue;

@RunWith(AndroidJUnit4.class)
public class ShoppingItemDaoTest extends DbTest {

	private ShoppingItemDao shoppingItemDao;
	private ShoppingListDao shoppingListDao;
	private long wishlistId;
	private long groceryListId;

	@Before
	public void initDb() {
		super.initDb();

		// shopping item won't exist without shopping list
		shoppingListDao = db.shoppingListDao();
		wishlistId = shoppingListDao.insert(new ShoppingList.Builder()//
				.title("My wishlist")
				.archived(false)
				.build());

		groceryListId = shoppingListDao.insert(new ShoppingList.Builder()//
				.title("Grocery")
				.archived(false)
				.build());

		shoppingItemDao = db.shoppingItemDao();

		final List<ShoppingItem> wishListItems = Arrays.asList(
				//@formatter:off
				new ShoppingItem.Builder()//
					.shoppingListId(wishlistId)
					.title("Samsung Galaxy S10")
					.build(),

				new ShoppingItem.Builder()//
					.shoppingListId(wishlistId)
					.title("iPhone 90s")
					.build()
				//@formatter:on
		);
		shoppingItemDao.insert(wishListItems);

	}

	@Test(expected = SQLiteConstraintException.class)
	public void insertingToNonExistingShoppingListShouldFail() {
		final long missingShoppingListId = wishlistId - 1;
		shoppingItemDao.insert(new ShoppingItem.Builder()//
				.shoppingListId(missingShoppingListId)
				.title("iPhone 80S")
				.created(millisOf(2017, 12, 31))
				.bought(false)
				.build());
	}

	@Test
	public void fetchingShoppingItems() throws InterruptedException {
		assertThat(getValue(shoppingItemDao.fetch(groceryListId)).size(), is(0));
		assertThat(getValue(shoppingItemDao.fetch(wishlistId)).size(), is(2));

		shoppingItemDao.insert(new ShoppingItem.Builder()//
				.shoppingListId(wishlistId)
				.build());
		assertThat(getValue(shoppingItemDao.fetch(wishlistId)).size(), is(3));
	}

	@Test
	public void addingShoppingItem() throws InterruptedException {
		assertThat(getValue(shoppingItemDao.fetch(groceryListId)).size(), is(0));

		final ShoppingItem.Builder newShoppingItemBuilder = new ShoppingItem.Builder()//
				.shoppingListId(groceryListId)
				.title("2 Carrots")
				.created(millisOf(2018, 1, 1))
				.bought(true);
		final long itemId = shoppingItemDao.insert(newShoppingItemBuilder.build());

		assertThat(getValue(shoppingItemDao.fetch(groceryListId)).get(0), is(newShoppingItemBuilder.id(itemId)
				.build()));
	}

	@Test
	public void updatingShoppingItem() throws InterruptedException {
		assertThat(getValue(shoppingItemDao.fetch(groceryListId)).size(), is(0));

		final ShoppingItem.Builder newShoppingItemBuilder = new ShoppingItem.Builder()//
				.shoppingListId(groceryListId)
				.title("2 Carrots")
				.created(millisOf(2018, 1, 1))
				.bought(true);
		final long itemId = shoppingItemDao.insert(newShoppingItemBuilder.build());

		assertThat(getValue(shoppingItemDao.fetch(groceryListId)).get(0), is(newShoppingItemBuilder.id(itemId)
				.build()));

		final ShoppingItem.Builder updatedShoppingItemBuilder = new ShoppingItem.Builder()//
				.id(itemId)
				.shoppingListId(groceryListId)
				.title("3 Carrots")
				.created(millisOf(2018, 1, 2))
				.bought(true);
		shoppingItemDao.insert(updatedShoppingItemBuilder.build());

		assertThat(getValue(shoppingItemDao.fetch(groceryListId)).get(0), is(updatedShoppingItemBuilder.build()));
	}

	@Test
	public void removingShoppingListShouldRemoveItsItems() throws InterruptedException {
		assertThat(getValue(shoppingItemDao.fetch(wishlistId)).size(), is(2));

		final ShoppingList wishlist = getValue(shoppingListDao.fetchById(wishlistId));
		shoppingListDao.remove(wishlist);

		assertThat(getValue(shoppingItemDao.fetch(wishlistId)).size(), is(0));
	}

	@Test
	public void removingShoppingItem() throws InterruptedException {
		final List<ShoppingItem> shoppingItems = getValue(shoppingItemDao.fetch(wishlistId));
		assertThat(shoppingItems.size(), is(2));

		shoppingItemDao.remove(shoppingItems.get(0));

		assertThat(getValue(shoppingItemDao.fetch(wishlistId)).size(), is(1));
	}

	@Test
	public void archivingShoppingListShouldNotRemoveItsItems() throws InterruptedException {
		assertThat(getValue(shoppingItemDao.fetch(wishlistId)).size(), is(2));

		final ShoppingList wishlist = getValue(shoppingListDao.fetchById(wishlistId));
		final ShoppingList updateWishlist = new ShoppingList.Builder(wishlist).archived(!wishlist.isArchived())
				.build();
		shoppingListDao.update(updateWishlist);

		assertThat(getValue(shoppingItemDao.fetch(wishlistId)).size(), is(2));
	}

}
