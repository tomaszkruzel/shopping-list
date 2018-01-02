package tomaszkruzel.shoppinglist.comparators;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Compares items using comparators in order passed to the constructor.
 * Next comparator is used only if previous one compared items to be equal.
 */
public class MultiComparator<T> implements Comparator<T> {

	private Comparator<T>[] comparators;

	@SafeVarargs
	public MultiComparator(@NonNull Comparator<T>... comparators) {
		this.comparators = comparators;
	}

	@Override
	public int compare(final T o1, final T o2) {
		int result = 0;
		for (final Comparator<T> comparator : comparators) {
			result = comparator.compare(o1, o2);
			if (result != 0) {
				break;
			}
		}
		return result;
	}
}
