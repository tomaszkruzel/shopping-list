package tomaszkruzel.shoppinglist.ui.activeshoppinglists;

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
import tomaszkruzel.shoppinglist.model.ShoppingList;
import tomaszkruzel.shoppinglist.utils.text.EditTextUtils;
import tomaszkruzel.shoppinglist.utils.text.NonEmptyTextWatcher;
import tomaszkruzel.shoppinglist.viewmodel.ActiveShoppingListsViewModel;

class EditShoppingListDialog extends AlertDialog {

	protected EditShoppingListDialog(@NonNull final Context context) {
		super(context);
	}

	protected EditShoppingListDialog(@NonNull final Context context, @StyleRes final int themeResId) {
		super(context, themeResId);
	}

	protected EditShoppingListDialog(@NonNull final Context context, final boolean cancelable, @Nullable final OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public static AlertDialog show(Activity activity, ActiveShoppingListsViewModel viewModel, ShoppingList shoppingList) {

		final EditShoppingListDialog dialog = new EditShoppingListDialog(activity);
		final LayoutInflater inflater = LayoutInflater.from(activity);
		View dialogView = inflater.inflate(R.layout.dialog_edit_shopping_list, null);
		dialog.setView(dialogView);
		dialog.show();

		final View saveButton = ButterKnife.findById(dialog, R.id.save_button);
		final EditText itemName = ButterKnife.findById(dialog, R.id.item_name);
		itemName.addTextChangedListener(new NonEmptyTextWatcher(isEmpty -> saveButton.setEnabled(!isEmpty)));
		EditTextUtils.setText(itemName, shoppingList.getTitle());

		saveButton.setOnClickListener(view -> {
			dialog.dismiss();
			viewModel.editShoppingListTitle(shoppingList, itemName.getText()
					.toString());
		});

		ButterKnife.findById(dialog, R.id.cancel_button)
				.setOnClickListener((View view) -> dialog.dismiss());

		return dialog;
	}
}
