package tomaszkruzel.shoppinglist.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import org.junit.After;
import org.junit.Before;

public abstract class DbTest {

	protected AppDatabase db;

	@Before
	public void initDb() {
		db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase.class)
				.build();
	}

	@After
	public void closeDb() {
		db.close();
	}
}
