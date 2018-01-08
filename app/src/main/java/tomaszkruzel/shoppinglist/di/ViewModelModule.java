/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tomaszkruzel.shoppinglist.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tomaszkruzel.shoppinglist.viewmodel.ActiveShoppingListsViewModel;
import tomaszkruzel.shoppinglist.viewmodel.AppViewModelFactory;
import tomaszkruzel.shoppinglist.viewmodel.ArchivedShoppingListsViewModel;
import tomaszkruzel.shoppinglist.viewmodel.ShoppingItemsViewModel;

@Module
abstract class ViewModelModule {

	@Binds
	@IntoMap
	@ViewModelKey(ActiveShoppingListsViewModel.class)
	abstract ViewModel bindActiveShoppingListsViewModel(ActiveShoppingListsViewModel activeShoppingListsViewModel);

	@Binds
	@IntoMap
	@ViewModelKey(ArchivedShoppingListsViewModel.class)
	abstract ViewModel bindArchivedShoppingListsViewModel(ArchivedShoppingListsViewModel archivedShoppingListsViewModel);

	@Binds
	@IntoMap
	@ViewModelKey(ShoppingItemsViewModel.class)
	abstract ViewModel bindShoppingItemsViewModel(ShoppingItemsViewModel shoppingItemsViewModel);

	@Binds
	abstract ViewModelProvider.Factory bindViewModelFactory(AppViewModelFactory factory);
}
