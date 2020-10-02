package hk.collaction.timber.ui.tree

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hk.collaction.timber.R
import java.util.*

/**
 * Created by himphen on 25/5/16.
 */
class TreeInfoItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mDataList: List<TreeInfoItem> = ArrayList()
    private var mOrientation: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = if ("horizontal" == mOrientation) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_info_horizontal, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_info, parent, false)
        }
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val treeInfoItem = mDataList[position]
        holder as ItemViewHolder
        holder.titleTv.text = treeInfoItem.titleText
        holder.contentTv.text = treeInfoItem.contentText
    }

    fun setData(mDataList: List<TreeInfoItem>, orientation: String?) {
        this.mDataList = mDataList
        mOrientation = orientation
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    internal class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTv: TextView = view.findViewById(R.id.titleTv)
        var contentTv: TextView = view.findViewById(R.id.contentTv)
    }
}