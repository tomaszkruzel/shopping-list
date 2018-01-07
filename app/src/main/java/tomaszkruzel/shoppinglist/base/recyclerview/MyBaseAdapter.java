package tomaszkruzel.shoppinglist.base.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import timber.log.Timber;

/**
 * Based on https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
 */
public abstract class MyBaseAdapter extends RecyclerView.Adapter<MyViewHolder> {

	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		final ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
		return new MyViewHolder(binding);
	}

	public void onBindViewHolder(MyViewHolder holder, int position) {
		final Object obj = getObjForPosition(position);
		holder.bind(obj);
		holder.itemView.setOnClickListener(v -> {
			int adapterPosition = holder.getAdapterPosition();
			if (adapterPosition != RecyclerView.NO_POSITION) {
				itemClickListener(adapterPosition);
			}
		});
		holder.itemView.setOnLongClickListener(v -> {
			int adapterPosition = holder.getAdapterPosition();
			if (adapterPosition != RecyclerView.NO_POSITION) {
				itemLongClickListener(adapterPosition);
				return true;
			}
			return false;
		});
	}

	@Override
	public int getItemViewType(int position) {
		return getLayoutIdForPosition(position);
	}

	protected abstract Object getObjForPosition(int position);

	protected abstract int getLayoutIdForPosition(int position);

	protected void itemClickListener(int adapterPosition) {
		Timber.d("itemClickListener not overridden!");
	}

	protected void itemLongClickListener(int adapterPosition) {
		Timber.d("itemLongClickListener not overridden!");
	}
}