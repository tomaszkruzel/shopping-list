package tomaszkruzel.shoppinglist.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class AppViewModelFactory implements ViewModelProvider.Factory {

	private final Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelProviders;

	@Inject
	public AppViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
		this.viewModelProviders = creators;
	}

	@SuppressWarnings("unchecked")
	@Override
	@NonNull
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		Provider<? extends ViewModel> viewModelProvider = viewModelProviders.get(modelClass);
		if (viewModelProvider == null) {
			for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : viewModelProviders.entrySet()) {
				if (modelClass.isAssignableFrom(entry.getKey())) {
					viewModelProvider = entry.getValue();
					break;
				}
			}
		}
		if (viewModelProvider == null) {
			throw new IllegalArgumentException("unknown model class " + modelClass);
		}
		try {
			return (T) viewModelProvider.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
