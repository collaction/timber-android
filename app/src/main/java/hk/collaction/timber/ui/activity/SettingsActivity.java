package hk.collaction.timber.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import hk.collaction.timber.R;
import hk.collaction.timber.ui.fragment.SettingsFragment;

public class SettingsActivity extends BaseActivity {

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		initActionBar(getSupportActionBar(), R.string.title_activity_settings);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);

		ButterKnife.bind(this);
		setSupportActionBar(toolbar);

		initActionBar(getSupportActionBar(), R.string.title_activity_settings);

		Fragment mainFragment = new SettingsFragment();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, mainFragment)
				.commit();
	}

}
