package tomaszkruzel.shoppinglist.testutils;

import java.util.GregorianCalendar;

public class DateTestUtil {

	/**
	 * @return milliseconds since Unix epoch for provided date time with default time zone.
	 */
	public static long millisOf(final int year, final int month, final int day, final int hour, final int minute) {
		return new GregorianCalendar(year, month - 1, day, hour, minute).getTimeInMillis();
	}

	/**
	 * @return milliseconds since Unix epoch for provided date with default time zone.
	 */
	public static long millisOf(final int year, final int month, final int day) {
		return millisOf(year, month, day, 0, 0);
	}

	public static final long januaryFirst = millisOf(2018, 1, 1);
}
