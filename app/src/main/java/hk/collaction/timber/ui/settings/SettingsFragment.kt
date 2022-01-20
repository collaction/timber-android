package hk.collaction.timber.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import hk.collaction.timber.R
import hk.collaction.timber.ui.base.BasePreferenceFragment
import hk.collaction.timber.utils.GeneralUtils.getAppVersionName
import hk.collaction.timber.utils.Utils.PREF_LANGUAGE
import hk.collaction.timber.utils.Utils.PREF_LANGUAGE_COUNTRY

class SettingsFragment : BasePreferenceFragment() {
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_general)
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference<Preference>("pref_language")!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                context?.let { context ->
                    MaterialAlertDialogBuilder(context)
                        .setTitle(R.string.action_language)
                        .setItems(R.array.language_choose) { dialog, index ->
                            dialog.dismiss()

                            val editor = preferences.edit()
                            when (index) {
                                1 -> editor.putString(PREF_LANGUAGE, "en")
                                    .putString(PREF_LANGUAGE_COUNTRY, "")
                                2 -> editor.putString(PREF_LANGUAGE, "zh")
                                    .putString(PREF_LANGUAGE_COUNTRY, "")
                                else -> editor.putString(PREF_LANGUAGE, "")
                                    .putString(PREF_LANGUAGE_COUNTRY, "")
                            }
                            editor.apply()

                            val intent =
                                context.packageManager?.getLaunchIntentForPackage(context.packageName)
                            intent?.let {
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                intent.addCategory(Intent.CATEGORY_HOME)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                        }
                        .setNegativeButton(R.string.ui_cancel, null)
                        .show()
                }
                false
            }
        findPreference<Preference>("pref_report")!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                var meta = """
                Android Version: ${Build.VERSION.RELEASE}

                """.trimIndent()
                meta += """
                SDK Level: ${Build.VERSION.SDK_INT}

                """.trimIndent()
                meta += """
                Version: ${getAppVersionName(context)}

                """.trimIndent()
                meta += """
                Brand: ${Build.BRAND}

                """.trimIndent()
                meta += """
                Model: ${Build.MODEL}

                """.trimIndent()
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("team@collaction.hk"))
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.pref_report_title))
                intent.putExtra(Intent.EXTRA_TEXT, meta)
                startActivity(Intent.createChooser(intent, getString(R.string.pref_report_title)))
                false
            }
        findPreference<Preference>("pref_rate")!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                val uri = Uri.parse("market://details?id=hk.collaction.timber")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                false
            }
        findPreference<Preference>("pref_share")!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(
                    Intent.EXTRA_TEXT, getString(R.string.pref_share_desc) +
                            "https://play.google.com/store/apps/details?id=hk.collaction.timber"
                )
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, getString(R.string.ui_share)))
                false
            }
        findPreference<Preference>("pref_author")!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                val uri = Uri.parse("market://search?q=pub:\"Collaction 小隊\"")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                false
            }
        findPreference<Preference>("pref_submit")!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                val uri = Uri.parse("https://www.collaction.hk/special/timber/submit")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                false
            }
        findPreference<Preference>("pref_version")!!.title =
            "Version " + getAppVersionName(context)
        findPreference<Preference>("pref_version")!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                val uri = Uri.parse("https://www.collaction.hk/s/timber")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                false
            }
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}