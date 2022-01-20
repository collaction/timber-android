package hk.collaction.timber.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import hk.collaction.timber.R
import hk.collaction.timber.api.model.Tree
import hk.collaction.timber.databinding.FragmentMainBinding
import hk.collaction.timber.ui.base.BaseFragment
import hk.collaction.timber.ui.settings.SettingsActivity
import hk.collaction.timber.ui.tree.TreeActivity
import hk.collaction.timber.ui.tree.TreeCard
import hk.collaction.timber.utils.Utils
import hk.collaction.timber.utils.Utils.getCurrentLanguage
import hk.collaction.timber.utils.Utils.getDisplaySize
import hk.collaction.timber.utils.ext.dpToPx
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by himphen on 21/5/16.
 */
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val vm: MainViewModel by viewModel()

    private val callback = object : TreeCard.Callback {
        override fun onSwipeOut() {
            if (viewBinding?.swipeView?.childCount == 1) {
                context?.let { context ->
                    vm.getTreeList(getCurrentLanguage(context))
                }
            }
        }

        override fun onSwipeIn(treeId: String) {
            vm.likeTree(treeId)
            if (viewBinding?.swipeView?.childCount == 1) {
                context?.let { context ->
                    vm.getTreeList(getCurrentLanguage(context))
                }
            }
        }

        override fun onClick(treeId: String) {
            val intent = Intent(context, TreeActivity::class.java)
            intent.putExtra(Utils.ARG_TREE_ID, treeId)
            context?.startActivity(intent)
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

        val viewBinding = viewBinding!!

        viewBinding.settingsBtn.setOnClickListener { onClickSettings() }

        val bottomMargin = dpToPx(160)

        activity?.let { activity ->
            val windowSize = getDisplaySize(activity)
            viewBinding.swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                ?.setDisplayViewCount(5)
                ?.setSwipeDecor(
                    SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setSwipeInMsgLayoutId(R.layout.tree_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tree_swipe_out_msg_view)
                        .setRelativeScale(0.01f)
                )
            viewBinding.rejectBtn.setOnClickListener { viewBinding.swipeView.doSwipe(false) }
            viewBinding.acceptBtn.setOnClickListener { viewBinding.swipeView.doSwipe(true) }

            vm.getTreeList(getCurrentLanguage(activity))
        }
    }

    private fun onGetTrees(trees: List<Tree>) {
        trees.forEach { tree ->
            viewBinding?.swipeView?.addView(
                TreeCard(tree, callback)
            )
        }
    }

    private fun onClickSettings() {
        startActivity(Intent(context, SettingsActivity::class.java))
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(inflater, container, false)
}