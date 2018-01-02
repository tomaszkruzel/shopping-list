package tomaszkruzel.shoppinglist.utils;

public abstract class LongUtils {

	public static int compare(long x, long y) {
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}
}
