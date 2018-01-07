package tomaszkruzel.shoppinglist.ui.activeshoppinglists;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import dagger.android.AndroidInjection;
import tomaszkruzel.shoppinglist.R;
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
		setContentView(R.layout.activity_shopping_lists);
		initViews();

		adapter = new ActiveShoppingListsAdapter(R.layout.item_shopping_list);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

		viewModel = ViewModelProviders.of(this, viewModelFactory)
				.get(ActiveShoppingListsViewModel.class);

		viewModel.getActiveShoppingLists()
				.observe(this, shoppingLists -> {
					if (shoppingLists != null) {
						adapter.updateItems(shoppingLists);
					}
				});
	}

	private void initViews() {
		recyclerView = findViewById(R.id.active_shoppping_lists);
	}

	public void addList(View view) {
		AddListDialog.show(this, viewModel);
	}
}
