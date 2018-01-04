package tomaszkruzel.shoppinglist;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import dagger.android.AndroidInjection;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.viewmodel.ActiveShoppingListsViewModel;

import javax.inject.Inject;

public class ActiveShoppingListsActivity extends AppCompatActivity {

	@Inject
	ViewModelProvider.Factory viewModelFactory;

	private TextView whatever;
	private ActiveShoppingListsViewModel viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		AndroidInjection.inject(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_lists);
		initViews();

		viewModel = ViewModelProviders.of(this, viewModelFactory)
				.get(ActiveShoppingListsViewModel.class);

		viewModel.getActiveShoppingLists()
				.observe(this, shoppingLists -> {
					if (shoppingLists != null) {
						whatever.setText(StreamSupport.stream(shoppingLists)
								.map(ShoppingList::toString)
								.collect(Collectors.joining("\n")));
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		viewModel.addShoppingList("onResume");
	}

	private void initViews() {
		whatever = findViewById(R.id.whatever);
	}
}
