package hk.collaction.timber.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.SizeUtils
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import hk.collaction.timber.R
import hk.collaction.timber.api.model.Tree
import hk.collaction.timber.ui.base.BaseFragment
import hk.collaction.timber.ui.settings.SettingsActivity
import hk.collaction.timber.ui.tree.TreeCard
import hk.collaction.timber.utils.Utils.getCurrentLanguage
import hk.collaction.timber.utils.Utils.getDisplaySize
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by himphen on 21/5/16.
 */
class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val vm: MainViewModel by viewModel()

    private val callback = object : TreeCard.Callback {
        override fun onSwipeOut() {
            if (swipeView.childCount == 1) {
                context?.let { context ->
                    vm.getTreeList(getCurrentLanguage(context))
                }
            }
        }

        override fun onSwipeIn(treeId: String) {
            vm.likeTree(treeId)
            if (swipeView.childCount == 1) {
                context?.let { context ->
                    vm.getTreeList(getCurrentLanguage(context))
                }
            }
        }
    }

    init {
        lifecycleScope.launchWhenCreated {
            vm.trees.observe(this@MainFragment, {
                onGetTrees(it)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsBtn.setOnClickListener { onClickSettings() }

        val bottomMargin = SizeUtils.dp2px(160f)

        activity?.let { activity ->
            val windowSize = getDisplaySize(activity)
            swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setDisplayViewCount(5)
                .setSwipeDecor(
                    SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setSwipeInMsgLayoutId(R.layout.tree_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tree_swipe_out_msg_view)
                        .setRelativeScale(0.01f)
                )
            rejectBtn.setOnClickListener { swipeView.doSwipe(false) }
            acceptBtn.setOnClickListener { swipeView.doSwipe(true) }

            vm.getTreeList(getCurrentLanguage(activity))
        }
    }

    private fun onGetTrees(trees: List<Tree>) {
        context?.let { context ->
            trees.forEach { tree ->
                swipeView.addView(
                    TreeCard(
                        context,
                        tree,
                        callback
                    )
                )
            }
        }
    }

    private fun onClickSettings() {
        startActivity(Intent(context, SettingsActivity::class.java))
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}