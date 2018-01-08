package tomaszkruzel.shoppinglist.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simple POJO that represents a shopping list.
 */
@Entity
public class ShoppingList implements Parcelable {

	@PrimaryKey(autoGenerate = true)
	private final long id;
	private final String title;
	private final long created;
	private final boolean archived;

	public ShoppingList(final long id, final String title, final long created, final boolean archived) {
		this.id = id;
		this.title = title;
		this.created = created;
		this.archived = archived;
	}

	private ShoppingList(final Builder builder) {
		id = builder.id;
		title = builder.title;
		created = builder.created;
		archived = builder.archived;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public long getCreated() {
		return created;
	}

	public boolean isArchived() {
		return archived;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		dest.writeLong(id);
		dest.writeString(title);
		dest.writeLong(created);
		dest.writeByte(archived ? (byte) 1 : (byte) 0);
	}

	protected ShoppingList(Parcel in) {
		id = in.readLong();
		title = in.readString();
		created = in.readLong();
		archived = in.readByte() != 0;
	}

	public static final Creator<ShoppingList> CREATOR = new Creator<ShoppingList>() {

		public ShoppingList createFromParcel(Parcel source) {
			return new ShoppingList(source);
		}

		public ShoppingList[] newArray(int size) {
			return new ShoppingList[size];
		}
	};

	public static final class Builder {

		private long id;
		private String title;
		private long created;
		private boolean archived;

		public Builder() {
		}

		public Builder(final ShoppingList copy) {
			this.id = copy.getId();
			this.title = copy.getTitle();
			this.created = copy.getCreated();
			this.archived = copy.isArchived();
		}

		public Builder id(final long val) {
			id = val;
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

		public Builder archived(final boolean val) {
			archived = val;
			return this;
		}

		public ShoppingList build() {
			return new ShoppingList(this);
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

		final ShoppingList that = (ShoppingList) o;

		if (id != that.id) {
			return false;
		}
		if (created != that.created) {
			return false;
		}
		if (archived != that.archived) {
			return false;
		}
		return title != null ? title.equals(that.title) : that.title == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (int) (created ^ (created >>> 32));
		result = 31 * result + (archived ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ShoppingList{" + "id=" + id + ", title='" + title + '\'' + ", created=" + created + ", archived=" + archived + '}';
	}
}
