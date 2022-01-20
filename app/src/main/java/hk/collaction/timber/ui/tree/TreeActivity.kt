package hk.collaction.timber.ui.tree

import android.os.Bundle
import hk.collaction.timber.R
import hk.collaction.timber.databinding.ActivityContainerBinding
import hk.collaction.timber.ui.base.BaseFragmentActivity
import hk.collaction.timber.utils.Utils.ARG_TREE_ID

class TreeActivity : BaseFragmentActivity<ActivityContainerBinding>() {
    override fun getActivityViewBinding() = ActivityContainerBinding.inflate(layoutInflater)
    override var titleId: Int? = R.string.app_name

    override fun onCreate(savedInstanceState: Bundle?) {
        fragment = TreeFragment.newInstance(Bundle().apply {
            putString(ARG_TREE_ID, intent.getStringExtra(ARG_TREE_ID))
        })

        super.onCreate(savedInstanceState)
    }
}