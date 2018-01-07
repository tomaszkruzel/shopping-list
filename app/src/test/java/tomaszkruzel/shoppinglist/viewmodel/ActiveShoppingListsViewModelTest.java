package tomaszkruzel.shoppinglist.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import tomaszkruzel.shoppinglist.businesslogic.manager.ShoppingListManager;
import tomaszkruzel.shoppinglist.db.ShoppingListDao;
import tomaszkruzel.shoppinglist.model.ShoppingList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ActiveShoppingListsViewModelTest {

	@Rule
	public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

	private ActiveShoppingListsViewModel viewModel;
	private ShoppingListDao shoppingListDao;
	private ShoppingListManager shoppingListManager;

	@Before
	public void init() {
		shoppingListDao = mock(ShoppingListDao.class);
		shoppingListManager = mock(ShoppingListManager.class);
		viewModel = new ActiveShoppingListsViewModel(shoppingListDao, shoppingListManager);
	}

	@Test
	public void noFetchWhenNotObserved() {
		viewModel.getActiveShoppingLists();
		verify(shoppingListDao, never()).fetchActive();
	}

	@Test
	public void fetchWhenObserved() {
		when(shoppingListDao.fetchActive()).thenReturn(new MutableLiveData<>());
		viewModel.getActiveShoppingLists()
				.observeForever(mock(Observer.class));
		verify(shoppingListDao, times(1)).fetchActive();
	}

	@Test
	public void addShoppingList() {
		final String title = "New shopping list";
		viewModel.addShoppingList(title);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(shoppingListManager).addShoppingList(captor.capture());
		assertThat(captor.getValue(), is(title));
	}

	@Test
	public void archiveShoppingList() {
		final ShoppingList shoppingList = new ShoppingList.Builder().build();
		viewModel.archive(shoppingList);
		ArgumentCaptor<ShoppingList> captor = ArgumentCaptor.forClass(ShoppingList.class);
		verify(shoppingListManager).archive(captor.capture());
		assertThat(captor.getValue(), is(shoppingList));
	}

	@Test
	public void removeShoppingList() {
		final ShoppingList shoppingList = new ShoppingList.Builder().build();
		viewModel.remove(shoppingList);
		ArgumentCaptor<ShoppingList> captor = ArgumentCaptor.forClass(ShoppingList.class);
		verify(shoppingListManager).remove(captor.capture());
		assertThat(captor.getValue(), is(shoppingList));
	}

}
