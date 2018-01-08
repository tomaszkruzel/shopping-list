package tomaszkruzel.shoppinglist.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import tomaszkruzel.shoppinglist.model.ShoppingItem;

import java.util.List;

@Dao
public interface ShoppingItemDao {

	@Query("SELECT * FROM ShoppingItem WHERE shoppingListId=:shoppingListId")
	LiveData<List<ShoppingItem>> fetch(long shoppingListId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	long insert(ShoppingItem shoppingItem);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(List<ShoppingItem> shoppingItems);

	@Delete
	void remove(ShoppingItem shoppingItems);
}
