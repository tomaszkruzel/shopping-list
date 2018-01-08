package tomaszkruzel.shoppinglist.utils.text;

import android.widget.EditText;

public class EditTextUtils {

	/**
	 * Sets text and ensures that cursor will be at the end of inserted text.
	 */
	public static void setText(EditText editText, String text) {
		editText.setText(text);
		editText.setSelection(editText.getText()
				.length());
	}
}
