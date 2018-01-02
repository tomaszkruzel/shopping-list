package tomaszkruzel.shoppinglist.db;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.testutils.DateTestUtil;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static tomaszkruzel.shoppinglist.testutils.LiveDataTestUtil.getValue;

@RunWith(AndroidJUnit4.class)
public class ShoppingListDaoTest extends DbTest {

	private ShoppingListDao shoppingListDao;

	@Before
	public void initDb() {
		super.initDb();

		final List<ShoppingList> initialTestData = Arrays.asList(
				//@formatter:off
				new ShoppingList.Builder()
						.title("Snowboard equipment")
						.archived(false)
						.build(),

				new ShoppingList.Builder()
						.title("My wishlist")
						.archived(false)
						.build(),

				new ShoppingList.Builder()
						.title("Grocery")
						.archived(false)
						.build(),

				new ShoppingList.Builder()
						.title("2017 Xmas gifts")
						.archived(true)
						.build(),

				new ShoppingList.Builder()
						.title("Friday")
						.archived(true)
						.build()
				//@formatter:on
		);

		shoppingListDao = db.shoppingListDao();
		shoppingListDao.persist(initialTestData);
	}

	@Test
	public void fetchingActiveShoppingLists() throws InterruptedException {
		assertThat(getValue(shoppingListDao.fetchActive()).size(), is(3));

		shoppingListDao.persist(new ShoppingList.Builder()//
				.archived(false)
				.build());
		assertThat(getValue(shoppingListDao.fetchActive()).size(), is(4));
	}

	@Test
	public void fetchingArchivedShoppingLists() throws InterruptedException {
		assertThat(getValue(shoppingListDao.fetchArchived()).size(), is(2));

		shoppingListDao.persist(new ShoppingList.Builder()//
				.archived(true)
				.build());
		assertThat(getValue(shoppingListDao.fetchArchived()).size(), is(3));
	}

	@Test
	public void addingShoppingList() throws InterruptedException {
		final ShoppingList.Builder newShoppingListBuilder = new ShoppingList.Builder()//
				.title("New Year's Eve Party")
				.created(DateTestUtil.millisOf(2017, 12, 30))
				.archived(false);

		final long id = shoppingListDao.persist(newShoppingListBuilder.build());

		assertThat(getValue(shoppingListDao.fetchById(id)), is(newShoppingListBuilder.id(id)
				.build()));
	}

	@Test
	public void updatingShoppingList() throws InterruptedException {
		final ShoppingList.Builder newShoppingListBuilder = new ShoppingList.Builder()//
				.title("New Year's Eve Party")
				.created(DateTestUtil.millisOf(2017, 12, 30))
				.archived(false);

		final long id = shoppingListDao.persist(newShoppingListBuilder.build());

		final ShoppingList.Builder updatedShoppingListBuilder = new ShoppingList.Builder()//
				.title("Friday Party")
				.created(DateTestUtil.millisOf(2018, 1, 1))
				.id(id)
				.archived(true);

		final ShoppingList updatedShoppingList = updatedShoppingListBuilder.build();
		shoppingListDao.persist(updatedShoppingList);

		assertThat(getValue(shoppingListDao.fetchById(id)), is(updatedShoppingList));
	}

}
