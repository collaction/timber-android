package hk.collaction.timber.ui.tree

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import coil.load
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Position
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.SwipeIn
import com.mindorks.placeholderview.annotations.swipe.SwipeOut
import hk.collaction.timber.R
import hk.collaction.timber.api.model.Tree
import java.util.Random

/**
 * Created by Himphen on 28/7/2017.
 */
@Layout(R.layout.tree_card_view)
class TreeCard(
    private val tree: Tree,
    private val callback: Callback
) {
    @View(R.id.profileImageView)
    lateinit var profileImageView: ImageView

    @View(R.id.infoLayout)
    lateinit var infoLayout: LinearLayout

    @View(R.id.nameAgeTxt)
    lateinit var nameAgeTxt: TextView

    @JvmField
    @Position
    var position: Int = 0

    @Resolve
    fun onResolved() {
        profileImageView.load(tree.photoPath!!.original) {
            placeholder(placeholderImageId)
            crossfade(true)
            crossfade(500)
        }

        nameAgeTxt.text = tree.name
        infoLayout.setOnClickListener {
        }
    }

    @Click(R.id.infoLayout)
    fun onClick() {
        tree.id?.let { treeId ->
            callback.onClick(treeId)
        }
    }

    private val placeholderImageId: Int
        get() = when (Random().nextInt(3)) {
            0 -> R.drawable.placeholder_1
            1 -> R.drawable.placeholder_2
            else -> R.drawable.placeholder_3
        }

    @SwipeOut
    fun onSwipeOut() {
        callback.onSwipeOut()
    }

    @SwipeIn
    fun onSwipeIn() {
        tree.id?.let { treeId ->
            callback.onSwipeIn(treeId)
        }
    }

    interface Callback {
        fun onSwipeOut()
        fun onSwipeIn(treeId: String)
        fun onClick(treeId: String)
    }
}