package tomaszkruzel.shoppinglist.ui.shoppingitems;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.base.recyclerview.SingleLayoutAdapter;
import tomaszkruzel.shoppinglist.model.ShoppingItem;
import tomaszkruzel.shoppinglist.viewmodel.ShoppingItemsViewModel;

import java.util.ArrayList;
import java.util.List;

class ShoppingItemsAdapter extends SingleLayoutAdapter {

	private List<ShoppingItem> items = new ArrayList<>();
	private boolean isArchivedList;
	private AppCompatActivity activity;
	private ShoppingItemsViewModel viewModel;

	ShoppingItemsAdapter(boolean isArchivedList, AppCompatActivity activity, ShoppingItemsViewModel viewModel) {
		super(R.layout.item_shopping_item);
		this.isArchivedList = isArchivedList;
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

	public void updateItems(final List<ShoppingItem> ShoppingItems) {
		items = ShoppingItems;
		notifyDataSetChanged(); // TODO DiffUtil
	}

	@Override
	protected void itemClickListener(final int adapterPosition) {
		if (!isArchivedList) {
			final ShoppingItem shoppingItem = items.get(adapterPosition);
			viewModel.toggleBought(shoppingItem);
		}
	}

	@Override
	protected void itemLongClickListener(final int adapterPosition) {
		if (!isArchivedList) {
			final ShoppingItem shoppingItem = items.get(adapterPosition);
			final ListAdapter listAdapter = new ArrayAdapter<>(activity, R.layout.item_dialog_text_row, activity.getResources()
					.getStringArray(R.array.shopping_item_options));
			new AlertDialog.Builder(activity).setTitle(shoppingItem.getTitle())
					.setSingleChoiceItems(listAdapter, -1, (dialog, which) -> {
						dialog.dismiss();
						switch (which) {
							case 0:
								EditShoppingItemDialog.newInstance(shoppingItem)
										.show(activity.getSupportFragmentManager(), EditShoppingItemDialog.class.getSimpleName());
								break;
							case 1:
								viewModel.remove(shoppingItem);
								break;
						}
					})
					.show();
		}
	}
}
