package hk.collaction.timber.core

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import hk.collaction.timber.utils.PreferenceUtils

class SharedPreferencesManager(val context: Context) {

    companion object {
        const val USER_NAME = "user_name"
    }

    private val preferences: SharedPreferences = PreferenceUtils.sharedPrefs(context)

    var userName: String
        get() = preferences.getString(USER_NAME, "") ?: ""
        set(value) {
            preferences.edit {
                putString(USER_NAME, value)
                apply()
            }
        }
}