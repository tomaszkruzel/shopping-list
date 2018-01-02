package tomaszkruzel.shoppinglist.model;

/**
 * Simple POJO that represents a shopping list.
 */
public class ShoppingList {

	private final long id;
	private final String title;
	private final long created;
	private final boolean archived;

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
