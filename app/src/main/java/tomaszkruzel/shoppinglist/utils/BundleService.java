package tomaszkruzel.shoppinglist.utils;

import android.os.Bundle;
import android.os.Parcelable;

public class BundleService {

	private final Bundle data;

	public BundleService(final Bundle savedState, final Bundle intentExtras) {
		this.data = new Bundle();

		if (null != savedState) {
			data.putAll(savedState);
		}

		if (null != intentExtras) {
			data.putAll(intentExtras);
		}
	}

	public Bundle getAll() {
		return data;
	}

	public Parcelable get(final String key) {
		return data.getParcelable(key);
	}
}
