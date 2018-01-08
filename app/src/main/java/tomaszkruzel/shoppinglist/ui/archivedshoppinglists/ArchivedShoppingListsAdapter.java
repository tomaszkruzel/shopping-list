package tomaszkruzel.shoppinglist.ui.archivedshoppinglists;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.base.recyclerview.SingleLayoutAdapter;
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.ui.shoppingitems.ShoppingItemsActivity;
import tomaszkruzel.shoppinglist.viewmodel.ArchivedShoppingListsViewModel;

import java.util.ArrayList;
import java.util.List;

class ArchivedShoppingListsAdapter extends SingleLayoutAdapter {

	private List<ShoppingList> items = new ArrayList<>();
	private Activity activity;
	private ArchivedShoppingListsViewModel viewModel;

	ArchivedShoppingListsAdapter(Activity activity, ArchivedShoppingListsViewModel viewModel) {
		super(R.layout.item_shopping_list);
		this.activity = activity;
		this.viewModel = viewModel;
	}

	@Override
	protected Object getObjForPosition(final int position) {
		return items.get(position);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void updateItems(final List<ShoppingList> shoppingLists) {
		items = shoppingLists;
		notifyDataSetChanged();
	}

	@Override
	protected void itemClickListener(final int adapterPosition) {
		final ShoppingList shoppingList = items.get(adapterPosition);
		activity.startActivity(ShoppingItemsActivity.newIntent(activity, shoppingList));
	}

	@Override
	protected void itemLongClickListener(final int adapterPosition) {
		final ShoppingList shoppingList = items.get(adapterPosition);
		final ListAdapter listAdapter = new ArrayAdapter<>(activity, R.layout.item_dialog_text_row, activity.getResources()
				.getStringArray(R.array.archived_shopping_list_options));
		new AlertDialog.Builder(activity).setTitle(shoppingList.getTitle())
				.setSingleChoiceItems(listAdapter, -1, (dialog, which) -> {
					dialog.dismiss();
					switch (which) {
						case 0:
							viewModel.unarchive(shoppingList);
							break;
						case 1:
							viewModel.remove(shoppingList);
							break;
					}
				})
				.show();
	}
}
