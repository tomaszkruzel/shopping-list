package tomaszkruzel.shoppinglist.ui.archivedshoppinglists;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import dagger.android.AndroidInjection;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.viewmodel.ArchivedShoppingListsViewModel;

import javax.inject.Inject;

public class ArchivedShoppingListsActivity extends AppCompatActivity {

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	private ArchivedShoppingListsViewModel viewModel;
	private RecyclerView recyclerView;
	private ArchivedShoppingListsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		AndroidInjection.inject(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archived_shopping_lists);
		initToolbar();
		initViews();

		viewModel = ViewModelProviders.of(this, viewModelFactory)
				.get(ArchivedShoppingListsViewModel.class);

		adapter = new ArchivedShoppingListsAdapter(R.layout.item_shopping_list, this, viewModel);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

		viewModel.getArchivedShoppingLists()
				.observe(this, shoppingLists -> {
					if (shoppingLists != null) {
						adapter.updateItems(shoppingLists);
					}
				});
	}

	private void initToolbar() {
		setTitle(R.string.title_archived_shopping_lists);
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
				viewModel.changeSortingOrder();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void initViews() {
		recyclerView = findViewById(R.id.archived_shoppping_lists);
	}
}
