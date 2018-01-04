package tomaszkruzel.shoppinglist.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tomaszkruzel.shoppinglist.viewmodel.ActiveShoppingListsViewModel;
import tomaszkruzel.shoppinglist.viewmodel.AppViewModelFactory;

@Module
abstract class ViewModelModule {

	@Binds
	@IntoMap
	@ViewModelKey(ActiveShoppingListsViewModel.class)
	abstract ViewModel bindActiveShoppingListsViewModel(ActiveShoppingListsViewModel activeShoppingListsViewModel);

	@Binds
	abstract ViewModelProvider.Factory bindViewModelFactory(AppViewModelFactory factory);
}
