package tomaszkruzel.shoppinglist.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tomaszkruzel.shoppinglist.ui.archivedshoppinglists.ArchivedShoppingListsActivity;

@Module
public abstract class ArchiveShoppingListsActivityModule {

	@ContributesAndroidInjector
	abstract ArchivedShoppingListsActivity contributeArchivedShoppingListsActivity();
}
