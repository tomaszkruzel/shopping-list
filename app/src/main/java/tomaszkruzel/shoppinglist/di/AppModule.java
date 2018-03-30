package tomaszkruzel.shoppinglist.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import dagger.Module;
import dagger.Provides;
import tomaszkruzel.shoppinglist.db.AppDatabase;
import tomaszkruzel.shoppinglist.db.ShoppingItemDao;
import tomaszkruzel.shoppinglist.db.ShoppingListDao;

import javax.inject.Singleton;

@Module(includes = { BusinessLogicModule.class })
class AppModule {

	@Singleton
	@Provides
	AppDatabase provideDb(Application app) {
		return Room.databaseBuilder(app, AppDatabase.class, "shoppinglist.db")
				.build();
	}

	@Singleton
	@Provides
	ShoppingListDao provideShoppingListDao(AppDatabase db) {
		return db.shoppingListDao();
	}

	@Singleton
	@Provides
	ShoppingItemDao provideShoppingItemDao(AppDatabase db) {
		return db.shoppingItemDao();
	}
}
