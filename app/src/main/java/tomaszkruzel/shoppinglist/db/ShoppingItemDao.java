package tomaszkruzel.shoppinglist.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import tomaszkruzel.shoppinglist.model.ShoppingItem;

import java.util.List;

@Dao
public interface ShoppingItemDao {

	@Query("SELECT * FROM ShoppingItem WHERE shoppingListId=:shoppingListId")
	LiveData<List<ShoppingItem>> fetch(long shoppingListId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	long persist(ShoppingItem shoppingItem);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void persist(List<ShoppingItem> shoppingItems);
}
