package tomaszkruzel.shoppinglist.ui.activeshoppinglists;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.utils.text.EditTextUtils;
import tomaszkruzel.shoppinglist.utils.text.NonEmptyTextWatcher;

public class EditShoppingListDialog extends DialogFragment {

	private static final String SHOPPING_LIST_KEY = "SHOPPING_LIST_KEY";

	interface EditShoppingListListener {

		void editShoppingListTitle(ShoppingList shoppingList, String title);
	}

	@SuppressLint("ValidFragment")
	protected EditShoppingListDialog() {
	}

	public static EditShoppingListDialog newInstance(@NonNull final ShoppingList shoppingList) {
		final Bundle args = new Bundle();
		args.putParcelable(SHOPPING_LIST_KEY, shoppingList);
		EditShoppingListDialog fragment = new EditShoppingListDialog();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onAttach(final Context context) {
		super.onAttach(context);
		if (!(getActivity() instanceof EditShoppingListListener)) {
			throw new IllegalStateException("Parent activity should be an instance of " + EditShoppingListListener.class);
		}
	}

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
			@Nullable final ViewGroup container,
			@Nullable final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_edit_shopping_list, container, false);
		final ShoppingList shoppingList = getArguments().getParcelable(SHOPPING_LIST_KEY);

		final View saveButton = view.findViewById(R.id.save_button);
		final EditText itemName = view.findViewById(R.id.item_name);
		itemName.addTextChangedListener(new NonEmptyTextWatcher(isEmpty -> saveButton.setEnabled(!isEmpty)));
		EditTextUtils.setText(itemName, shoppingList.getTitle());

		saveButton.setOnClickListener(v -> {
			dismiss();
			((EditShoppingListListener) getActivity()).editShoppingListTitle(shoppingList, itemName.getText()
					.toString());
		});

		view.findViewById(R.id.cancel_button)
				.setOnClickListener(v -> dismiss());

		return view;
	}
}
