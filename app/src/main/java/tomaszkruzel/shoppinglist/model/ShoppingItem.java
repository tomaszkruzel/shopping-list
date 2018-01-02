package tomaszkruzel.shoppinglist.model;

/**
 * Simple POJO that represents item on the shopping list.
 */
public class ShoppingItem {

	private final long id;
	private final String title;
	private final long created;
	private final boolean bought;

	private ShoppingItem(final Builder builder) {
		id = builder.id;
		title = builder.title;
		created = builder.created;
		bought = builder.bought;
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

	public boolean isBought() {
		return bought;
	}

	public static final class Builder {

		private long id;
		private String title;
		private long created;
		private boolean bought;

		public Builder() {
		}

		public Builder(final ShoppingItem copy) {
			this.id = copy.getId();
			this.title = copy.getTitle();
			this.created = copy.getCreated();
			this.bought = copy.isBought();
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
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (int) (created ^ (created >>> 32));
		result = 31 * result + (bought ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ShoppingItem{" + "id=" + id + ", title='" + title + '\'' + ", created=" + created + ", bought=" + bought + '}';
	}
}
