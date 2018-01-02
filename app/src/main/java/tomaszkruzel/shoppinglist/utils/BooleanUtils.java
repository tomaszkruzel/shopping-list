package tomaszkruzel.shoppinglist.utils;

public abstract class BooleanUtils {

	public static int compare(boolean x, boolean y) {
		return (x == y) ? 0 : (x ? 1 : -1);
	}
}
