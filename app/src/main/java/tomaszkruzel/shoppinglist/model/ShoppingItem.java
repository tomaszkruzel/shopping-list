package tomaszkruzel.shoppinglist.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Simple POJO that represents item on the shopping list.
 */
@Entity(foreignKeys = @ForeignKey(entity = ShoppingList.class,
								  parentColumns = "id",
								  childColumns = "shoppingListId",
								  onUpdate = ForeignKey.CASCADE,
								  onDelete = ForeignKey.CASCADE))
public class ShoppingItem {

	@PrimaryKey(autoGenerate = true)
	private final long id;
	private final long shoppingListId;
	private final String title;
	private final long created;
	private final boolean bought;

	public ShoppingItem(final long id, final long shoppingListId, final String title, final long created, final boolean bought) {
		this.id = id;
		this.shoppingListId = shoppingListId;
		this.title = title;
		this.created = created;
		this.bought = bought;
	}

	public long getId() {
		return id;
	}

	public long getShoppingListId() {
		return shoppingListId;
	}

	public String getTitle() {
		return title;
	}

	public long getCreated() {
		return created;
	}

	public boolean isBought() {
		return bought;
	}

	private ShoppingItem(final Builder builder) {
		id = builder.id;
		shoppingListId = builder.shoppingListId;
		title = builder.title;
		created = builder.created;
		bought = builder.bought;
	}

	public static final class Builder {

		private long id;
		private long shoppingListId;
		private String title;
		private long created;
		private boolean bought;

		public Builder() {
		}

		public Builder(final ShoppingItem copy) {
			this.id = copy.getId();
			this.shoppingListId = copy.getShoppingListId();
			this.title = copy.getTitle();
			this.created = copy.getCreated();
			this.bought = copy.isBought();
		}

		public Builder id(final long val) {
			id = val;
			return this;
		}

		public Builder shoppingListId(final long val) {
			shoppingListId = val;
			return this;
		}

		public Builder title(final String val) {
			title = val;
			return this;
		}

		public Builder created(final long val) {
			created = val;
			return this;
		}

		public Builder bought(final boolean val) {
			bought = val;
			return this;
		}

		public ShoppingItem build() {
			return new ShoppingItem(this);
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final ShoppingItem that = (ShoppingItem) o;

		if (id != that.id) {
			return false;
		}
		if (shoppingListId != that.shoppingListId) {
			return false;
		}
		if (created != that.created) {
			return false;
		}
		if (bought != that.bought) {
			return false;
		}
		return title != null ? title.equals(that.title) : that.title == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (shoppingListId ^ (shoppingListId >>> 32));
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (int) (created ^ (created >>> 32));
		result = 31 * result + (bought ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ShoppingItem{" + "id=" + id + ", shoppingListId=" + shoppingListId + ", title='" + title + '\'' + ", created=" + created
				+ ", bought=" + bought + '}';
	}
}
