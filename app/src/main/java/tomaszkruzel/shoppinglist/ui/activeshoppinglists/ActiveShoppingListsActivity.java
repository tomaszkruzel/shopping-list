package tomaszkruzel.shoppinglist.ui.activeshoppinglists;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.ui.BaseDaggerAppCompatActivity;
import tomaszkruzel.shoppinglist.ui.archivedshoppinglists.ArchivedShoppingListsActivity;
import tomaszkruzel.shoppinglist.viewmodel.ActiveShoppingListsViewModel;
import tomaszkruzel.shoppinglist.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class ActiveShoppingListsActivity extends BaseDaggerAppCompatActivity
		implements AddShoppingListDialog.AddShoppingListListener, EditShoppingListDialog.EditShoppingListListener {

	@Inject
	ViewModelFactory<ActiveShoppingListsViewModel> viewModelFactory;

	private ActiveShoppingListsViewModel viewModel;
	private RecyclerView recyclerView;
	private View sortingOptions;
	private ActiveShoppingListsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_shopping_lists);
		initToolbar();
		initViews();

		viewModel = ViewModelProviders.of(this, viewModelFactory)
				.get(ActiveShoppingListsViewModel.class);

		adapter = new ActiveShoppingListsAdapter(this, viewModel);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

		viewModel.getActiveShoppingLists()
				.observe(this, shoppingLists -> {
					if (shoppingLists != null) {
						adapter.updateItems(shoppingLists);
						sortingOptions.setVisibility(shoppingLists.size() > 1 ? View.VISIBLE : View.GONE);
					}
				});
	}

	private void initToolbar() {
		setTitle(R.string.title_active_shopping_lists);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_active_shopping_lists, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_go_to_archive:
				startActivity(new Intent(this, ArchivedShoppingListsActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void initViews() {
		recyclerView = findViewById(R.id.active_shoppping_lists);
		sortingOptions = findViewById(R.id.sorting_options);
	}

	public void addList(View view) {
		new AddShoppingListDialog().show(getSupportFragmentManager(), AddShoppingListDialog.class.getSimpleName());
	}

	@Override
	public void addShoppingList(final String title) {
		viewModel.addShoppingList(title);
	}

	@Override
	public void editShoppingListTitle(final ShoppingList shoppingList, final String title) {
		viewModel.editShoppingListTitle(shoppingList, title);
	}

	public void showSortingOptions(View view) {
		final ListAdapter listAdapter = new ArrayAdapter<>(this, R.layout.item_dialog_text_row,
				getResources().getStringArray(R.array.dialog_sorting_shopping_lists));
		new AlertDialog.Builder(this).setTitle(getString(R.string.dialog_sorting_options))
				.setSingleChoiceItems(listAdapter, -1, (dialog, which) -> {
					dialog.dismiss();
					switch (which) {
						case 0:
							viewModel.changeSortingOrder(true);
							break;
						case 1:
							viewModel.changeSortingOrder(false);
							break;
					}
				})
				.show();
	}
}
