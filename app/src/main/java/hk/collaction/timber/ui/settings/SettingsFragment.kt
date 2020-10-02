package hk.collaction.timber.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.blankj.utilcode.util.AppUtils
import hk.collaction.timber.R
import hk.collaction.timber.ui.base.BasePreferenceFragment
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
                val language = preferences.getString(PREF_LANGUAGE, "")
                val languageCountry = preferences.getString(PREF_LANGUAGE_COUNTRY, "")
                var a = 0
                if ("en" == language) {
                    a = 1
                } else if ("zh" == language) {
                    a = 2
                }
                context?.let { context ->
                    val dialog = MaterialDialog(context)
                        .title(R.string.action_language)
                        .listItemsSingleChoice(R.array.language_choose, initialSelection = a) { dialog: MaterialDialog, index: Int, text: CharSequence ->
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
                            startActivity(Intent(context, SettingsActivity::class.java))
                            activity?.finish()
                        }
                        .negativeButton(R.string.ui_cancel)
                    dialog.show()
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
                Version: ${AppUtils.getAppVersionName()}

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
            "Version " + AppUtils.getAppVersionName()
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