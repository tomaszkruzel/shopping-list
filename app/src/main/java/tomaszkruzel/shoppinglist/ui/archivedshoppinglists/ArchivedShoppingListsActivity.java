package tomaszkruzel.shoppinglist.ui.archivedshoppinglists;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import dagger.android.AndroidInjection;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.viewmodel.ArchivedShoppingListsViewModel;

import javax.inject.Inject;

public class ArchivedShoppingListsActivity extends AppCompatActivity {

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	private ArchivedShoppingListsViewModel viewModel;
	private RecyclerView recyclerView;
	private View sortingOptions;
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

		adapter = new ArchivedShoppingListsAdapter(this, viewModel);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

		viewModel.getArchivedShoppingLists()
				.observe(this, shoppingLists -> {
					if (shoppingLists != null) {
						adapter.updateItems(shoppingLists);
						sortingOptions.setVisibility(shoppingLists.size() > 1 ? View.VISIBLE : View.GONE);
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
		recyclerView = findViewById(R.id.archived_shoppping_lists);
		sortingOptions = findViewById(R.id.sorting_options);
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
