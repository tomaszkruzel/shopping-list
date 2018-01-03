package tomaszkruzel.shoppinglist.businesslogic.manager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import tomaszkruzel.shoppinglist.businesslogic.time.TimeProvider;
import tomaszkruzel.shoppinglist.db.ShoppingListDao;
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.testutils.InstantAppExecutors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static tomaszkruzel.shoppinglist.testutils.DateTestUtil.januaryFirst;
import static tomaszkruzel.shoppinglist.testutils.DateTestUtil.millisOf;

@RunWith(JUnit4.class)
public class ShoppingListManagerImplTest {

	private ShoppingListManagerImpl shoppingListManager;
	private ShoppingListDao shoppingListDao;
	private TimeProvider timeProvider;

	@Before
	public void init() {
		shoppingListDao = mock(ShoppingListDao.class);
		timeProvider = mock(TimeProvider.class);
		shoppingListManager = new ShoppingListManagerImpl(shoppingListDao, timeProvider, new InstantAppExecutors());
	}

	@Test
	public void addShoppingList() {
		final Long newYearsMillis = millisOf(2018, 1, 1);
		when(timeProvider.getMillisecondSinceUnixEpoch()).thenReturn(newYearsMillis);

		final String title = "2018 wishlist";
		shoppingListManager.addShoppingList(title);

		final ArgumentCaptor<ShoppingList> captor = ArgumentCaptor.forClass(ShoppingList.class);
		verify(shoppingListDao).persist(captor.capture());

		final ShoppingList shoppingList = captor.getValue();
		assertThat(shoppingList.getTitle(), is(title));
		assertThat(shoppingList.getCreated(), is(newYearsMillis));
		assertThat(shoppingList.isArchived(), is(false));
	}

	@Test
	public void archiveShoppingList() {
		final ShoppingList shoppingList = new ShoppingList.Builder()//
				.id(12)
				.title("2018 wishlist")
				.created(januaryFirst)
				.archived(false)
				.build();

		shoppingListManager.archive(shoppingList);

		final ArgumentCaptor<ShoppingList> captor = ArgumentCaptor.forClass(ShoppingList.class);
		verify(shoppingListDao).persist(captor.capture());

		final ShoppingList modifiedShoppingList = new ShoppingList.Builder(shoppingList)//
				.archived(true)
				.build();
		assertThat(captor.getValue(), is(modifiedShoppingList));
	}

	@Test
	public void removeShoppingList() {
		final ShoppingList shoppingList = new ShoppingList.Builder()//
				.id(12)
				.title("2018 wishlist")
				.created(januaryFirst)
				.archived(false)
				.build();

		shoppingListManager.remove(shoppingList);

		final ArgumentCaptor<ShoppingList> captor = ArgumentCaptor.forClass(ShoppingList.class);
		verify(shoppingListDao).remove(captor.capture());

		assertThat(captor.getValue(), is(shoppingList));
	}
}
