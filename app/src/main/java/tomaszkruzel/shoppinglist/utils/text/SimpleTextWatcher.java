package tomaszkruzel.shoppinglist.utils.text;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Useful for {@link TextWatcher} implementation that don't care about all interface methods.
 */
public class SimpleTextWatcher implements TextWatcher {

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}
}