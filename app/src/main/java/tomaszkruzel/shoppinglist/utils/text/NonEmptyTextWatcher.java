package tomaszkruzel.shoppinglist.utils.text;

import android.support.annotation.NonNull;
import android.text.Editable;
import tomaszkruzel.shoppinglist.utils.StringUtils;

/**
 * Notifies via {@link Callback} whether entered text is empty or not.
 */
public class NonEmptyTextWatcher extends SimpleTextWatcher {

	public interface Callback {

		void textEmpty(boolean isEmpty);
	}

	private final Callback callback;

	public NonEmptyTextWatcher(@NonNull Callback callback) {
		this.callback = callback;
	}

	@Override
	public void afterTextChanged(Editable s) {
		callback.textEmpty(StringUtils.isEmpty(s.toString()));
	}
}