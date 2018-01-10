package tomaszkruzel.shoppinglist.model;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static tomaszkruzel.shoppinglist.testutils.DateTestUtil.januaryFirst;

@RunWith(AndroidJUnit4.class)
public class ShoppingItemTest {

	@Test
	public void toAndFromParcel() {
		final ShoppingItem shoppingItem = new ShoppingItem.Builder()//
				.id(123L)
				.shoppingListId(321L)
				.title("title")
				.created(januaryFirst)
				.bought(true)
				.build();

		final Parcel parcel = Parcel.obtain();
		shoppingItem.writeToParcel(parcel, 0);

		// reset the parcel for reading
		parcel.setDataPosition(0);

		// Reconstruct object from parcel and asserts:
		ShoppingItem createdFromParcel = shoppingItem.CREATOR.createFromParcel(parcel);
		assertEquals(shoppingItem, createdFromParcel);
	}
}
