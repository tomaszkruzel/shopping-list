package tomaszkruzel.shoppinglist.ui.activeshoppinglists;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.utils.text.NonEmptyTextWatcher;

public class AddShoppingListDialog extends DialogFragment {

	interface AddShoppingListListener {

		void addShoppingList(String title);
	}

	public AddShoppingListDialog() {
	}

	@Override
	public void onAttach(final Context context) {
		super.onAttach(context);
		if (!(getActivity() instanceof AddShoppingListListener)) {
			throw new IllegalStateException("Parent activity should be an instance of " + AddShoppingListListener.class);
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view = inflater.inflate(R.layout.dialog_add_shopping_list, null);

		final View addButton = view.findViewById(R.id.add_button);
		final EditText listName = view.findViewById(R.id.list_name);
		listName.addTextChangedListener(new NonEmptyTextWatcher(isEmpty -> addButton.setEnabled(!isEmpty)));

		addButton.setOnClickListener(v -> {
			dismiss();
			((AddShoppingListListener) getActivity()).addShoppingList(listName.getText()
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
