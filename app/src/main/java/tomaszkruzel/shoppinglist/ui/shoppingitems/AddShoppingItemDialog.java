package tomaszkruzel.shoppinglist.ui.shoppingitems;

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

public class AddShoppingItemDialog extends DialogFragment {

	interface AddShoppingItemListener {

		void addShoppingItem(String title);
	}

	public AddShoppingItemDialog() {
	}

	@Override
	public void onAttach(final Context context) {
		super.onAttach(context);
		if (!(getActivity() instanceof AddShoppingItemListener)) {
			throw new IllegalStateException("Parent activity should be an instance of " + AddShoppingItemListener.class);
		}
	}

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater,
			@Nullable final ViewGroup container,
			@Nullable final Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_add_shopping_item, container, false);

		final View addButton = view.findViewById(R.id.add_button);
		final EditText itemName = view.findViewById(R.id.item_name);
		itemName.addTextChangedListener(new NonEmptyTextWatcher(isEmpty -> addButton.setEnabled(!isEmpty)));

		addButton.setOnClickListener(v -> {
			dismiss();
			((AddShoppingItemListener) getActivity()).addShoppingItem(itemName.getText()
					.toString());
		});

		view.findViewById(R.id.cancel_button)
				.setOnClickListener(v -> dismiss());

		return view;
	}
}
