package tomaszkruzel.shoppinglist.ui.shoppingitems;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.model.ShoppingItem;
import tomaszkruzel.shoppinglist.model.ShoppingItemSortingOption;
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.ui.BaseDaggerAppCompatActivity;
import tomaszkruzel.shoppinglist.viewmodel.ShoppingItemsViewModel;
import tomaszkruzel.shoppinglist.viewmodel.ViewModelFactory;

import javax.inject.Inject;
import javax.inject.Named;

public class ShoppingItemsActivity extends BaseDaggerAppCompatActivity
		implements AddShoppingItemDialog.AddShoppingItemListener, EditShoppingItemDialog.EditShoppingItemListener {

	public static final String SHOPPING_LIST_KEY = "shopping_list";

	public static Intent newIntent(Context context, ShoppingList shoppingList) {
		final Intent intent = new Intent(context, ShoppingItemsActivity.class);
		intent.putExtra(SHOPPING_LIST_KEY, shoppingList);
		return intent;
	}

	@Inject
	ViewModelFactory<ShoppingItemsViewModel> viewModelFactory;

	@Inject
	@Named(ShoppingItemsActivity.SHOPPING_LIST_KEY)
	ShoppingList shoppingList;

	private ShoppingItemsViewModel viewModel;
	private RecyclerView recyclerView;
	private View addItem;
	private View sortingOptions;
	private ShoppingItemsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_items);
		initToolbar();
		initViews();

		setTitle(getTitle(shoppingList));

		viewModel = ViewModelProviders.of(this, viewModelFactory)
				.get(ShoppingItemsViewModel.class);

		adapter = new ShoppingItemsAdapter(shoppingList.isArchived(), this, viewModel);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

		addItem.setVisibility(shoppingList.isArchived() ? View.GONE : View.VISIBLE);
		addItem.setOnClickListener(
				v -> new AddShoppingItemDialog().show(getSupportFragmentManager(), AddShoppingItemDialog.class.getSimpleName()));

		viewModel.getShoppingItems()
				.observe(this, shoppingItems -> {
					if (shoppingItems != null) {
						adapter.updateItems(shoppingItems);
						sortingOptions.setVisibility(shoppingItems.size() > 1 ? View.VISIBLE : View.GONE);
					}
				});
	}

	@NonNull
	private String getTitle(final ShoppingList shoppingList) {
		return shoppingList.getTitle() + (shoppingList.isArchived() ? getString(R.string.archived_suffix) : "");
	}

	private void initToolbar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void initViews() {
		recyclerView = findViewById(R.id.shopping_items);
		addItem = findViewById(R.id.add_item);
		sortingOptions = findViewById(R.id.sorting_options);
	}

	@Override
	public void addShoppingItem(final String title) {
		viewModel.addShoppingItem(title);
	}

	@Override
	public void editShoppingItemTitle(final ShoppingItem shoppingItem, final String title) {
		viewModel.editShoppingItemTitle(shoppingItem, title);
	}

	public void showSortingOptions(View view) {
		final ListAdapter listAdapter = new ArrayAdapter<>(this, R.layout.item_dialog_text_row,
				getResources().getStringArray(R.array.dialog_sorting_shopping_items));
		new AlertDialog.Builder(this).setTitle(getString(R.string.dialog_sorting_options))
				.setSingleChoiceItems(listAdapter, -1, (dialog, which) -> {
					dialog.dismiss();
					viewModel.changeSortingOption(getSortingOption(which));
				})
				.show();
	}

	private ShoppingItemSortingOption getSortingOption(final int which) {
		switch (which) {
			case 0:
				return ShoppingItemSortingOption.OLDEST_TO_NEWEST;
			case 1:
				return ShoppingItemSortingOption.NEWEST_TO_OLDEST;
			case 2:
				return ShoppingItemSortingOption.NOT_BOUGHT_FIRST_OLDEST_TO_NEWEST;
			case 3:
				return ShoppingItemSortingOption.NOT_BOUGHT_FIRST_NEWEST_TO_OLDEST;
			case 4:
				return ShoppingItemSortingOption.BOUGHT_FIRST_OLDEST_TO_NEWEST;
			case 5:
				return ShoppingItemSortingOption.BOUGHT_FIRST_NEWEST_TO_OLDEST;
		}
		throw new IllegalStateException("Illegal which value");
	}
}
