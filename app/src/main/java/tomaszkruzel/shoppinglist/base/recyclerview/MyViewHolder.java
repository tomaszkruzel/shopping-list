package tomaszkruzel.shoppinglist.base.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import tomaszkruzel.shoppinglist.BR;

/**
 * Based on https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
 */
public class MyViewHolder extends RecyclerView.ViewHolder {

	private final ViewDataBinding binding;

	public MyViewHolder(ViewDataBinding binding) {
		super(binding.getRoot());
		this.binding = binding;
	}

	public void bind(Object item) {
		binding.setVariable(BR.item, item);
		binding.executePendingBindings();
	}
}
