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
import tomaszkruzel.shoppinglist.utils.text.NonEmptyTextWatcher;
import tomaszkruzel.shoppinglist.viewmodel.ActiveShoppingListsViewModel;

public class AddListDialog extends AlertDialog {

	protected AddListDialog(@NonNull final Context context) {
		super(context);
	}

	protected AddListDialog(@NonNull final Context context, @StyleRes final int themeResId) {
		super(context, themeResId);
	}

	protected AddListDialog(@NonNull final Context context, final boolean cancelable, @Nullable final OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public static AlertDialog show(Activity activity, ActiveShoppingListsViewModel viewModel) {

		final AddListDialog dialog = new AddListDialog(activity);
		final LayoutInflater inflater = LayoutInflater.from(activity);
		View dialogView = inflater.inflate(R.layout.dialog_add_list, null);
		dialog.setView(dialogView);
		dialog.show();

		final View addButton = ButterKnife.findById(dialog, R.id.add_button);
		final EditText listName = ButterKnife.findById(dialog, R.id.list_name);
		listName.addTextChangedListener(new NonEmptyTextWatcher(isEmpty -> addButton.setEnabled(!isEmpty)));

		addButton.setOnClickListener(view -> {
			dialog.dismiss();
			viewModel.addShoppingList(listName.getText()
					.toString());
		});

		ButterKnife.findById(dialog, R.id.cancel_button)
				.setOnClickListener((View view) -> dialog.dismiss());

		return dialog;
	}
}
