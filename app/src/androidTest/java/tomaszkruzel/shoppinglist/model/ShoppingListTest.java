package tomaszkruzel.shoppinglist.model;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static tomaszkruzel.shoppinglist.testutils.DateTestUtil.januaryFirst;

@RunWith(AndroidJUnit4.class)
public class ShoppingListTest {

	@Test
	public void toAndFromParcel() {
		final ShoppingList shoppingList = new ShoppingList.Builder()//
				.id(123L)
				.title("title")
				.created(januaryFirst)
				.archived(true)
				.build();

		final Parcel parcel = Parcel.obtain();
		shoppingList.writeToParcel(parcel, 0);

		// reset the parcel for reading
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		ShoppingList createdFromParcel = ShoppingList.CREATOR.createFromParcel(parcel);
		assertEquals(shoppingList, createdFromParcel);
	}
}
