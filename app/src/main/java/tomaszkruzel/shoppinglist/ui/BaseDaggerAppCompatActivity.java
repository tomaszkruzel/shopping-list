package tomaszkruzel.shoppinglist.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import dagger.android.support.DaggerAppCompatActivity;
import tomaszkruzel.shoppinglist.utils.BundleService;

public abstract class BaseDaggerAppCompatActivity extends DaggerAppCompatActivity {

	private BundleService bundleService;

	public BundleService getBundleService() {
		return bundleService;
	}

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		// initialize BundleService before this activity is injected to object graph
		bundleService = new BundleService(savedInstanceState, getIntent().getExtras());
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putAll(bundleService.getAll());
	}
}
