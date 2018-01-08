package tomaszkruzel.shoppinglist.ui.shoppingitems;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import dagger.android.AndroidInjection;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.viewmodel.ShoppingItemsViewModel;

import javax.inject.Inject;

public class ShoppingItemsActivity extends AppCompatActivity {

	private static final String SHOPPING_LIST_KEY = "shopping_list";
	private View addItem;

	public static Intent newIntent(Context context, ShoppingList shoppingList) {
		final Intent intent = new Intent(context, ShoppingItemsActivity.class);
		intent.putExtra(SHOPPING_LIST_KEY, shoppingList);
		return intent;
	}

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	private ShoppingItemsViewModel viewModel;
	private RecyclerView recyclerView;
	private ShoppingItemsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		AndroidInjection.inject(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_items);
		initToolbar();
		initViews();

		final ShoppingList shoppingList = getIntent().getParcelableExtra(SHOPPING_LIST_KEY);
		setTitle(getTitle(shoppingList));

		viewModel = ViewModelProviders.of(this, viewModelFactory)
				.get(ShoppingItemsViewModel.class);
		viewModel.init(shoppingList.getId());

		adapter = new ShoppingItemsAdapter(shoppingList.isArchived(), this, viewModel);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

		addItem.setVisibility(shoppingList.isArchived() ? View.GONE : View.VISIBLE);
		addItem.setOnClickListener(v -> AddShoppingItemDialog.show(this, viewModel));

		viewModel.getShoppingItems()
				.observe(this, shoppingItems -> {
					if (shoppingItems != null) {
						adapter.updateItems(shoppingItems);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_archived_shopping_lists, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			case R.id.action_sort:
				if (adapter.getItemCount() > 1) {
					viewModel.changeSortingOrder();
				} else {
					Toast.makeText(this, R.string.nothing_to_sort, Toast.LENGTH_SHORT)
							.show();
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void initViews() {
		recyclerView = findViewById(R.id.shopping_items);
		addItem = findViewById(R.id.add_item);
	}
}
