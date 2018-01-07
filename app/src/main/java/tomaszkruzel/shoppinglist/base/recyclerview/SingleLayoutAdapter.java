package tomaszkruzel.shoppinglist.base.recyclerview;

import android.support.annotation.LayoutRes;

/**
 * Based on https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
 */
public abstract class SingleLayoutAdapter extends MyBaseAdapter {

	private final int layoutId;

	public SingleLayoutAdapter(@LayoutRes int layoutId) {
		this.layoutId = layoutId;
	}

	@Override
	protected int getLayoutIdForPosition(int position) {
		return layoutId;
	}
}