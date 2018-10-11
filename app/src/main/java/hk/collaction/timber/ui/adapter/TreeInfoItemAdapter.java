package hk.collaction.timber.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hk.collaction.timber.R;
import hk.collaction.timber.model.TreeInfoItem;

/**
 * Created by himphen on 25/5/16.
 */
public class TreeInfoItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<TreeInfoItem> mDataList = new ArrayList<>();
	private String mOrientation;

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();

		View itemView;

		if ("horizontal".equals(mOrientation)) {
			itemView = LayoutInflater.from(context).inflate(R.layout.list_item_info_horizontal, parent, false);
		} else {
			itemView = LayoutInflater.from(context).inflate(R.layout.list_item_info, parent, false);
		}

		return new ItemViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rawHolder, int position) {
		TreeInfoItem item = mDataList.get(position);
		ItemViewHolder holder = (ItemViewHolder) rawHolder;

		holder.titleTv.setText(item.getTitleText());
		holder.contentTv.setText(item.getContentText());
	}

	public void setData(List<TreeInfoItem> mDataList, String orientation) {
		this.mDataList = mDataList;
		this.mOrientation = orientation;

		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return mDataList.size();
	}

	static class ItemViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.titleTv)
		TextView titleTv;
		@BindView(R.id.contentTv)
		TextView contentTv;

		ItemViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}