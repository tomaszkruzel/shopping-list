package tomaszkruzel.shoppinglist.businesslogic.time;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TimeProviderImpl implements TimeProvider {

	@Inject
	public TimeProviderImpl() {
	}

	@Override
	public long getMillisecondSinceUnixEpoch() {
		return System.currentTimeMillis();
	}
}
