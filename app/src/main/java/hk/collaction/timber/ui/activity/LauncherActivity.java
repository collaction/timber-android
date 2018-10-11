package hk.collaction.timber.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import hk.collaction.timber.R;

public class LauncherActivity extends AppCompatActivity {

	private Activity mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		mContext = this;
		ButterKnife.bind(this);

		checkAppVersion();
	}

	private void checkAppVersion() {
		new Handler().postDelayed(
				new Runnable() {
					public void run() {
						goToNextActivity();
					}
				},
				2500);
	}

	private void goToNextActivity() {
		Intent mainIntent = new Intent(LauncherActivity.this, MainActivity.class);
		startActivity(mainIntent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	@Override
	public void onBackPressed() {

	}
}
