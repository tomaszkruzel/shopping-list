package tomaszkruzel.shoppinglist.businesslogic.manager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import tomaszkruzel.shoppinglist.businesslogic.time.TimeProvider;
import tomaszkruzel.shoppinglist.db.ShoppingItemDao;
import tomaszkruzel.shoppinglist.model.ShoppingItem;
import tomaszkruzel.shoppinglist.testutils.InstantAppExecutors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static tomaszkruzel.shoppinglist.testutils.DateTestUtil.januaryFirst;
import static tomaszkruzel.shoppinglist.testutils.DateTestUtil.millisOf;

@RunWith(JUnit4.class)
public class ShoppingItemManagerImplTest {

	private ShoppingItemManagerImpl shoppingItemManager;
	private ShoppingItemDao shoppingItemDao;
	private TimeProvider timeProvider;

	@Before
	public void init() {
		shoppingItemDao = mock(ShoppingItemDao.class);
		timeProvider = mock(TimeProvider.class);
		shoppingItemManager = new ShoppingItemManagerImpl(shoppingItemDao, timeProvider, new InstantAppExecutors());
	}

	@Test
	public void addShoppingItem() {
		final Long newYearsMillis = millisOf(2018, 1, 1);
		when(timeProvider.getMillisecondSinceUnixEpoch()).thenReturn(newYearsMillis);

		final String title = "iPhone 10X";
		final long shoppingListId = 2L;
		shoppingItemManager.addShoppingItem(title, shoppingListId);

		final ArgumentCaptor<ShoppingItem> captor = ArgumentCaptor.forClass(ShoppingItem.class);
		verify(shoppingItemDao).insert(captor.capture());

		final ShoppingItem shoppingItem = captor.getValue();
		assertThat(shoppingItem.getTitle(), is(title));
		assertThat(shoppingItem.getShoppingListId(), is(shoppingListId));
		assertThat(shoppingItem.getCreated(), is(newYearsMillis));
		assertThat(shoppingItem.isBought(), is(false));
	}

	@Test
	public void editShoppingItemTitle() {
		final ShoppingItem shoppingItem = new ShoppingItem.Builder()//
				.id(12)
				.shoppingListId(23L)
				.title("snowboard")
				.created(januaryFirst)
				.bought(true)
				.build();

		final String newTitle = "skates";
		shoppingItemManager.editShoppingItemTitle(shoppingItem, newTitle);

		final ArgumentCaptor<ShoppingItem> captor = ArgumentCaptor.forClass(ShoppingItem.class);
		verify(shoppingItemDao).insert(captor.capture());

		final ShoppingItem editedShoppingItem = captor.getValue();
		assertThat(editedShoppingItem.getTitle(), is(newTitle));
		assertThat(editedShoppingItem.getShoppingListId(), is(shoppingItem.getShoppingListId()));
		assertThat(editedShoppingItem.getCreated(), is(shoppingItem.getCreated()));
		assertThat(editedShoppingItem.isBought(), is(shoppingItem.isBought()));
	}

	@Test
	public void toggleBoughtFromBought() {
		final ShoppingItem shoppingItem = new ShoppingItem.Builder()//
				.id(12)
				.shoppingListId(23L)
				.title("snowboard")
				.created(januaryFirst)
				.bought(true)
				.build();

		shoppingItemManager.toggleBought(shoppingItem);

		ArgumentCaptor<ShoppingItem> captor = ArgumentCaptor.forClass(ShoppingItem.class);
		verify(shoppingItemDao).insert(captor.capture());

		final ShoppingItem editedShoppingItem = captor.getValue();
		assertThat(editedShoppingItem.getTitle(), is(editedShoppingItem.getTitle()));
		assertThat(editedShoppingItem.getShoppingListId(), is(shoppingItem.getShoppingListId()));
		assertThat(editedShoppingItem.getCreated(), is(shoppingItem.getCreated()));
		assertThat(editedShoppingItem.isBought(), is(false));
	}

	@Test
	public void toggleBoughtFromNotBought() {
		final ShoppingItem shoppingItem = new ShoppingItem.Builder()//
				.id(12)
				.shoppingListId(23L)
				.title("snowboard")
				.created(januaryFirst)
				.bought(false)
				.build();

		shoppingItemManager.toggleBought(shoppingItem);

		ArgumentCaptor<ShoppingItem> captor = ArgumentCaptor.forClass(ShoppingItem.class);
		verify(shoppingItemDao).insert(captor.capture());

		final ShoppingItem editedShoppingItem = captor.getValue();
		assertThat(editedShoppingItem.getTitle(), is(editedShoppingItem.getTitle()));
		assertThat(editedShoppingItem.getShoppingListId(), is(shoppingItem.getShoppingListId()));
		assertThat(editedShoppingItem.getCreated(), is(shoppingItem.getCreated()));
		assertThat(editedShoppingItem.isBought(), is(true));
	}

	@Test
	public void removeShoppingItem() {
		final ShoppingItem shoppingItem = new ShoppingItem.Builder()//
				.id(12)
				.shoppingListId(23L)
				.title("snowboard")
				.created(januaryFirst)
				.bought(true)
				.build();

		shoppingItemManager.remove(shoppingItem);

		final ArgumentCaptor<ShoppingItem> captor = ArgumentCaptor.forClass(ShoppingItem.class);
		verify(shoppingItemDao).remove(captor.capture());

		assertThat(captor.getValue(), is(shoppingItem));
	}
}
