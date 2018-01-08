package tomaszkruzel.shoppinglist.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import tomaszkruzel.shoppinglist.model.ShoppingList;

import java.util.List;

@Dao
public interface ShoppingListDao {

	@Query("SELECT * FROM shoppinglist WHERE archived=0")
	LiveData<List<ShoppingList>> fetchActive();

	@Query("SELECT * FROM shoppinglist WHERE archived=1")
	LiveData<List<ShoppingList>> fetchArchived();

	@Query("SELECT * FROM shoppinglist WHERE id=:id")
	LiveData<ShoppingList> fetchById(long id);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	long insert(ShoppingList shoppingList);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(List<ShoppingList> shoppingList);

	@Update
	void update(ShoppingList shoppingList);

	@Delete
	void remove(ShoppingList shoppingList);
}
