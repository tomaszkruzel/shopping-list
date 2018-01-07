package tomaszkruzel.shoppinglist.ui.activeshoppinglists;

import android.support.annotation.LayoutRes;
import tomaszkruzel.shoppinglist.base.recyclerview.SingleLayoutAdapter;
import tomaszkruzel.shoppinglist.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

class ActiveShoppingListsAdapter extends SingleLayoutAdapter {

	private List<ShoppingList> items = new ArrayList<>();

	public ActiveShoppingListsAdapter(@LayoutRes final int layoutId) {
		super(layoutId);
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
}
