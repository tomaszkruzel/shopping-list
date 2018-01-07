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
