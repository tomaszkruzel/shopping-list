package tomaszkruzel.shoppinglist.ui.activeshoppinglists;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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
import tomaszkruzel.shoppinglist.ui.archivedshoppinglists.ArchivedShoppingListsActivity;
import tomaszkruzel.shoppinglist.viewmodel.ActiveShoppingListsViewModel;

import javax.inject.Inject;

public class ActiveShoppingListsActivity extends AppCompatActivity {

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	private ActiveShoppingListsViewModel viewModel;
	private RecyclerView recyclerView;
	private ActiveShoppingListsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		AndroidInjection.inject(this);
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
			case R.id.action_sort:
				if (adapter.getItemCount() > 1) {
					viewModel.changeSortingOrder();
				} else {
					Toast.makeText(this, R.string.nothing_to_sort, Toast.LENGTH_SHORT)
							.show();
				}
				return true;
			case R.id.action_go_to_archive:
				startActivity(new Intent(this, ArchivedShoppingListsActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void initViews() {
		recyclerView = findViewById(R.id.active_shoppping_lists);
	}

	public void addList(View view) {
		AddListDialog.show(this, viewModel);
	}
}
