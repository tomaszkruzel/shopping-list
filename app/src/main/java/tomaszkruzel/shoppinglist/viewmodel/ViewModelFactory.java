package tomaszkruzel.shoppinglist.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import dagger.Lazy;

import javax.inject.Inject;

public class ViewModelFactory<VM extends ViewModel> implements ViewModelProvider.Factory {

	// Lazy value in order to prevent Dagger creating redundant ViewModels on configuration change
	Lazy<VM> viewModel;

	@Inject
	public ViewModelFactory(final Lazy<VM> viewModel) {
		this.viewModel = viewModel;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
		return (T) viewModel.get();
	}
}
