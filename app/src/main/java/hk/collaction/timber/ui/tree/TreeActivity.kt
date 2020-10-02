package hk.collaction.timber.ui.tree

import android.os.Bundle
import hk.collaction.timber.R
import hk.collaction.timber.ui.base.BaseFragmentActivity
import hk.collaction.timber.utils.Utils.ARG_TREE_ID

class TreeActivity : BaseFragmentActivity() {
    override var titleId: Int? = R.string.app_name

    override fun onCreate(savedInstanceState: Bundle?) {
        val args = Bundle()
        args.putString(ARG_TREE_ID, intent.getStringExtra(ARG_TREE_ID))
        fragment = TreeFragment.newInstance(args)

        super.onCreate(savedInstanceState)
    }
}