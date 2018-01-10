package tomaszkruzel.shoppinglist.ui.activeshoppinglists;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.utils.text.NonEmptyTextWatcher;

public class AddListDialog extends DialogFragment {

	interface AddShoppingListListener {

		void addShoppingList(String title);
	}

	public AddListDialog() {
	}

	@Override
	public void onAttach(final Context context) {
		super.onAttach(context);
		if (!(getActivity() instanceof AddShoppingListListener)) {
			throw new IllegalStateException("Parent activity should be an instance of " + AddShoppingListListener.class);
		}
	}

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
			@Nullable final ViewGroup container,
			@Nullable final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.dialog_add_shopping_list, container, false);

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

		return view;
	}
}
