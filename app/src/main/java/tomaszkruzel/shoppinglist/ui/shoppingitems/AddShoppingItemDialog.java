package tomaszkruzel.shoppinglist.ui.shoppingitems;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import butterknife.ButterKnife;
import tomaszkruzel.shoppinglist.R;
import tomaszkruzel.shoppinglist.utils.text.NonEmptyTextWatcher;
import tomaszkruzel.shoppinglist.viewmodel.ShoppingItemsViewModel;

class AddShoppingItemDialog extends AlertDialog {

	protected AddShoppingItemDialog(@NonNull final Context context) {
		super(context);
	}

	protected AddShoppingItemDialog(@NonNull final Context context, @StyleRes final int themeResId) {
		super(context, themeResId);
	}

	protected AddShoppingItemDialog(@NonNull final Context context, final boolean cancelable, @Nullable final OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public static AlertDialog show(Activity activity, ShoppingItemsViewModel viewModel) {

		final AddShoppingItemDialog dialog = new AddShoppingItemDialog(activity);
		final LayoutInflater inflater = LayoutInflater.from(activity);
		View dialogView = inflater.inflate(R.layout.dialog_add_shopping_item, null);
		dialog.setView(dialogView);
		dialog.show();

		final View addButton = ButterKnife.findById(dialog, R.id.add_button);
		final EditText itemName = ButterKnife.findById(dialog, R.id.item_name);
		itemName.addTextChangedListener(new NonEmptyTextWatcher(isEmpty -> addButton.setEnabled(!isEmpty)));

		addButton.setOnClickListener(view -> {
			dialog.dismiss();
			viewModel.addShoppingItem(itemName.getText()
					.toString());
		});

		ButterKnife.findById(dialog, R.id.cancel_button)
				.setOnClickListener((View view) -> dialog.dismiss());

		return dialog;
	}
}
