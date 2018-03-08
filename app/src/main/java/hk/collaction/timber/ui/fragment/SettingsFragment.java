package hk.collaction.timber.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.preference.Preference;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import hk.collaction.timber.C;
import hk.collaction.timber.R;
import hk.collaction.timber.ui.activity.SettingsActivity;


public class SettingsFragment extends BasePreferenceFragment {

	private SharedPreferences settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref_general);
		settings = PreferenceManager.getDefaultSharedPreferences(mContext);
	}

	@Override
	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		findPreference("pref_language").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				String language = settings.getString(C.PREF_LANGUAGE, "auto");
				int a = 0;
				switch (language) {
					case "auto":
						a = 0;
						break;
					case "en":
						a = 1;
						break;
					case "zh":
						a = 2;
						break;
				}

				MaterialDialog.Builder dialog = new MaterialDialog.Builder(mContext)
						.title(R.string.action_language)
						.items(R.array.language_choose)
						.itemsCallbackSingleChoice(a, new MaterialDialog.ListCallbackSingleChoice() {
							@Override
							public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
								switch (which) {
									case 0:
										settings.edit().putString(C.PREF_LANGUAGE, "auto").apply();
										break;
									case 1:
										settings.edit().putString(C.PREF_LANGUAGE, "en").apply();
										break;
									case 2:
										settings.edit().putString(C.PREF_LANGUAGE, "zh").apply();
										break;
								}
								startActivity(new Intent(mContext, SettingsActivity.class));
								mContext.finish();
								return false;
							}
						})
						.negativeText(R.string.ui_cancel);
				dialog.show();

				return false;
			}
		});

		findPreference("pref_report").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				String meta = "Android Version: " + Build.VERSION.RELEASE + "\n";
				meta += "SDK Level: " + Build.VERSION.SDK_INT + "\n";
				meta += "Version: " + C.getCurrentVersionName(mContext) + "\n";
				meta += "Brand: " + Build.BRAND + "\n";
				meta += "Model: " + Build.MODEL + "\n";

				Intent intent = new Intent(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse("mailto:"));
				intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"team@collaction.hk"});
				intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.pref_report_title));
				intent.putExtra(Intent.EXTRA_TEXT, meta);

				startActivity(Intent.createChooser(intent, getString(R.string.pref_report_title)));
				return false;
			}
		});

		findPreference("pref_rate").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Uri uri = Uri.parse("market://details?id=hk.collaction.timber");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				return false;
			}
		});

		findPreference("pref_share").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEND);
				intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.pref_share_desc) +
						"https://play.google.com/store/apps/details?id=hk.collaction.timber");
				intent.setType("text/plain");
				startActivity(Intent.createChooser(intent, getString(R.string.ui_share)));
				return false;
			}
		});

		findPreference("pref_author").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Uri uri = Uri.parse("market://search?q=pub:\"Collaction 小隊\"");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				return false;
			}
		});

		findPreference("pref_submit").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Uri uri = Uri.parse("https://www.collaction.hk/special/timber/submit");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				return false;
			}
		});

		findPreference("pref_version").setTitle("Version " + C.getCurrentVersionName(mContext));

		findPreference("pref_version").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Uri uri = Uri.parse("https://www.collaction.hk/s/timber");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				return false;
			}
		});

	}

}