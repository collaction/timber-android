package hk.collaction.timber.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import hk.collaction.timber.C;
import hk.collaction.timber.R;
import hk.collaction.timber.ui.fragment.TreeFragment;

public class TreeActivity extends BaseActivity {

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		ActionBar ab = initActionBar(getSupportActionBar(), R.string.app_name);
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setHomeButtonEnabled(false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container);

		ButterKnife.bind(this);
		setSupportActionBar(toolbar);

		ActionBar ab = initActionBar(getSupportActionBar(), R.string.app_name);
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setHomeButtonEnabled(false);

		Bundle args = new Bundle();
		args.putInt(C.ARG_TREE_ID, getIntent().getIntExtra(C.ARG_TREE_ID, 0));

		Fragment mainFragment = TreeFragment.newInstance(args);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, mainFragment)
				.commit();
	}

}
