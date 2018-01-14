package tomaszkruzel.shoppinglist.ui.shoppingitems;

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
import tomaszkruzel.shoppinglist.model.ShoppingItem;
import tomaszkruzel.shoppinglist.utils.text.EditTextUtils;
import tomaszkruzel.shoppinglist.utils.text.NonEmptyTextWatcher;

public class EditShoppingItemDialog extends DialogFragment {

	private static final String SHOPPING_ITEM_KEY = "SHOPPING_ITEM_KEY";

	interface EditShoppingItemListener {

		void editShoppingItemTitle(ShoppingItem shoppingItem, String title);
	}

	@SuppressLint("ValidFragment")
	protected EditShoppingItemDialog() {
	}

	public static EditShoppingItemDialog newInstance(@NonNull final ShoppingItem shoppingItem) {
		final Bundle args = new Bundle();
		args.putParcelable(SHOPPING_ITEM_KEY, shoppingItem);
		EditShoppingItemDialog fragment = new EditShoppingItemDialog();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onAttach(final Context context) {
		super.onAttach(context);
		if (!(getActivity() instanceof EditShoppingItemListener)) {
			throw new IllegalStateException("Parent activity should be an instance of " + EditShoppingItemListener.class);
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_edit_shopping_item, null);

		final ShoppingItem shoppingItem = getArguments().getParcelable(SHOPPING_ITEM_KEY);

		final View saveButton = view.findViewById(R.id.save_button);
		final EditText itemName = view.findViewById(R.id.item_name);
		itemName.addTextChangedListener(new NonEmptyTextWatcher(isEmpty -> saveButton.setEnabled(!isEmpty)));
		EditTextUtils.setText(itemName, shoppingItem.getTitle());

		saveButton.setOnClickListener(v -> {
			dismiss();
			((EditShoppingItemListener) getActivity()).editShoppingItemTitle(shoppingItem, itemName.getText()
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
