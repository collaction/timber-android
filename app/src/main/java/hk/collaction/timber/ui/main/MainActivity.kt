package hk.collaction.timber.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import hk.collaction.timber.R
import hk.collaction.timber.databinding.ActivityContainerBinding
import hk.collaction.timber.ui.base.BaseFragmentActivity

class MainActivity : BaseFragmentActivity<ActivityContainerBinding>() {
    override fun getActivityViewBinding() = ActivityContainerBinding.inflate(layoutInflater)

    override var fragment: Fragment? = MainFragment()
    override var titleId: Int? = R.string.app_name

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }
}