package hk.collaction.timber.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Locale;

import hk.collaction.timber.R;

/**
 * UtilHelper Class
 * Created by Himphen on 10/1/2016.
 */
@SuppressWarnings("unused")
public class UtilHelper {

	public static final String PREF_IAP = "iap";
	public static final String PREF_LANGUAGE = "PREF_LANGUAGE";
	public static final String PREF_LANGUAGE_COUNTRY = "PREF_LANGUAGE_COUNTRY";

	@SuppressWarnings("JavaReflectionMemberAccess")
	public static void forceShowMenu(Context mContext) {
		try {
			ViewConfiguration config = ViewConfiguration.get(mContext);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ignored) {
		}
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static String formatSignificant(double value, int significant) {
		MathContext mathContext = new MathContext(significant, RoundingMode.DOWN);
		BigDecimal bigDecimal = new BigDecimal(value, mathContext);
		return bigDecimal.toPlainString();
	}

	public static String getCurrentLanguage(Context mContext) {
		return mContext.getString(R.string.ui_lang);
	}

	public static void detectLanguage(Context mContext) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		String language = preferences.getString(UtilHelper.PREF_LANGUAGE, "");
		String languageCountry = preferences.getString(UtilHelper.PREF_LANGUAGE_COUNTRY, "");

		if (language.equals("")) {
			Locale locale;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				locale = Resources.getSystem().getConfiguration().getLocales().get(0);
			} else {
				locale = Resources.getSystem().getConfiguration().locale;
			}
			language = locale.getLanguage();
			languageCountry = locale.getCountry();
		}

		if (mContext instanceof LocalizationActivity) {
			((LocalizationActivity) mContext).setLanguage(new Locale(language, languageCountry));
		} else {
			Resources res = mContext.getResources();
			Configuration conf = res.getConfiguration();

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				conf.setLocale(new Locale(language, languageCountry));
			} else {
				conf.locale = new Locale(language, languageCountry);
			}

			DisplayMetrics dm = res.getDisplayMetrics();
			res.updateConfiguration(conf, dm);
		}
	}

	public static int getColor(Context mContext, int resId) {
		return ContextCompat.getColor(mContext, resId);
	}

	public static Point getDisplaySize(WindowManager windowManager) {
		try {
			Display display = windowManager.getDefaultDisplay();
			DisplayMetrics displayMetrics = new DisplayMetrics();
			display.getMetrics(displayMetrics);
			return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
		} catch (Exception e) {
			e.printStackTrace();
			return new Point(0, 0);
		}
	}
}
