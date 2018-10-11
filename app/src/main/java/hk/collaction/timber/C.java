package hk.collaction.timber;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ConvertUtils;
import com.crashlytics.android.Crashlytics;

import hk.collaction.timber.helper.UtilHelper;
import hk.collaction.timber.rest.ApiConfig;
import retrofit2.Response;

public class C extends UtilHelper {

	public static final String TAG = "TAG";
	public static final String DEBUG_TAG = "debug_tag";
	public static final String ARG_TREE_ID = "ARG_TREE_ID";

	public static void errorDialog(Context context, Response response) {
		String errorMessage = ConvertUtils.inputStream2String(response.errorBody().byteStream(), "UTF-8");
		Crashlytics.log(errorMessage);

		errorDialog(context, (String) null);
	}

	public static void errorDialog(Context context, Throwable throwable) {
		if (ApiConfig.BASE_URL.isDev()) {
			throwable.printStackTrace();
		}

		Crashlytics.logException(throwable);

		errorDialog(context, (String) null);
	}

	public static void errorDialog(Context context, String errorMessage) {
		if (context == null) {
			return;
		}

		if (errorMessage == null) {
			errorMessage = context.getString(R.string.ui_fatal_error);
		}

		MaterialDialog.Builder builder =
				new MaterialDialog.Builder(context)
						.title(R.string.ui_sorry)
						.content(errorMessage)
						.positiveText(R.string.ui_nvm);
		if (scanForActivity(context).hasWindowFocus()) {
			builder.show();
		}
	}

	public static void generalDialog(Context context, String errorMessage) {
		if (context == null) {
			return;
		}

		MaterialDialog.Builder builder =
				new MaterialDialog.Builder(context)
						.title(R.string.ui_sorry)
						.content(errorMessage)
						.positiveText(R.string.ui_okay);
		if (scanForActivity(context).hasWindowFocus()) {
			builder.show();
		}
	}

	public static void log(String message) {
		Log.d(C.TAG, message);
	}

	public static void debug(String message) {
		if (ApiConfig.BASE_URL.isDev()) {
			Log.d(C.DEBUG_TAG, message);
		}
	}

	private static Activity scanForActivity(Context cont) {
		if (cont == null)
			return null;
		else if (cont instanceof Activity)
			return (Activity) cont;
		else if (cont instanceof ContextWrapper)
			return scanForActivity(((ContextWrapper) cont).getBaseContext());

		return null;
	}
}
