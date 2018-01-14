package tomaszkruzel.shoppinglist.ui.activeshoppinglists;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view = inflater.inflate(R.layout.dialog_edit_shopping_list, null);

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

		final AlertDialog dialog = new AlertDialog.Builder(getActivity())//
				.setView(view)
				.create();

		dialog.getWindow()
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		return dialog;
	}
}
