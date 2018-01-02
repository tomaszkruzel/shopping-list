package tomaszkruzel.shoppinglist.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import tomaszkruzel.shoppinglist.model.ShoppingList;

@Database(entities = { ShoppingList.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

	public abstract ShoppingListDao shoppingListDao();
}
