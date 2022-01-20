package hk.collaction.timber.ui.settings

import androidx.fragment.app.Fragment
import hk.collaction.timber.R
import hk.collaction.timber.databinding.ActivityContainerBinding
import hk.collaction.timber.ui.base.BaseFragmentActivity

class SettingsActivity : BaseFragmentActivity<ActivityContainerBinding>() {
    override fun getActivityViewBinding() = ActivityContainerBinding.inflate(layoutInflater)
    override var fragment: Fragment? = SettingsFragment.newInstance()
    override var titleId: Int? = R.string.title_activity_settings
}