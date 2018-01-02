package tomaszkruzel.shoppinglist.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
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
	long persist(ShoppingList shoppingList);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void persist(List<ShoppingList> shoppingList);
}
