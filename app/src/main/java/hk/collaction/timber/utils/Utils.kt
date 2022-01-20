package hk.collaction.timber.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.crashlytics.FirebaseCrashlytics
import hk.collaction.timber.BuildConfig
import hk.collaction.timber.R
import java.util.Locale

/**
 * UtilHelper Class
 * Created by Himphen on 10/1/2016.
 */
object Utils {
    const val PREF_IAP = "iap"
    const val PREF_LANGUAGE = "PREF_LANGUAGE"
    const val PREF_LANGUAGE_COUNTRY = "PREF_LANGUAGE_COUNTRY"

    fun getCurrentLanguage(context: Context): String {
        return context.getString(R.string.ui_lang)
    }

    fun updateLanguage(context: Context): Context {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val language = preferences.getString(PREF_LANGUAGE, "") ?: ""
        val languageCountry = preferences.getString(PREF_LANGUAGE_COUNTRY, "") ?: ""
        if (language.isNotEmpty()) {
            val config = context.resources.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(Locale(language, languageCountry))
            } else {
                @Suppress("DEPRECATION")
                config.locale = Locale(language, languageCountry)
            }

            return context.createConfigurationContext(config)
        }

        return context
    }

    fun getDisplaySize(activity: Activity): Point {
        return try {
            val display = activity.windowManager.defaultDisplay
            val displayMetrics = DisplayMetrics()
            display.getMetrics(displayMetrics)
            Point(displayMetrics.widthPixels, displayMetrics.heightPixels)
        } catch (e: Exception) {
            e.printStackTrace()
            Point(0, 0)
        }
    }

    fun isPermissionsGranted(context: Context?, permissions: Array<String>): Boolean {
        context?.let {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        it,
                        permission
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    return false
                }
            }
            return true
        }
        return false

    }

    fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }


    const val TAG = "TAG"
    const val DEBUG_TAG = "debug_tag"
    const val ARG_TREE_ID = "ARG_TREE_ID"

    fun errorDialog(context: Context?, message: String? = null) {
        if (context == null) return

        var errorMessage = message
        if (errorMessage == null) {
            errorMessage = context.getString(R.string.ui_fatal_error)
        }

        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(R.string.ui_sorry)
                .setMessage(errorMessage)
                .setPositiveButton(R.string.ui_okay, null)
                .setNegativeButton(R.string.ui_report) { _, index: Int ->
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://m.me/snappyscheme"))
                    context.startActivity(i)
                }
                .setCancelable(false)
                .show()
        }
    }

    fun logException(e: Exception) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        } else {
            FirebaseCrashlytics.getInstance().recordException(e)
        }
    }
}