package hk.collaction.timber.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hk.collaction.timber.R;
import hk.collaction.timber.model.InfoItem;

/**
 * Created by himphen on 25/5/16.
 */
public class InfoItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<InfoItem> mDataList;
	private String mOrientation;

	public InfoItemAdapter(List<InfoItem> mDataList, String orientation) {
		this.mDataList = mDataList;
		this.mOrientation = orientation;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();

		View itemView;

		if (mOrientation.equals("horizontal")) {
			itemView = LayoutInflater.from(context).inflate(R.layout.list_item_info_horizontal, parent, false);
		} else {
			itemView = LayoutInflater.from(context).inflate(R.layout.list_item_info, parent, false);
		}

		return new ItemViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rawHolder, int position) {
		InfoItem item = mDataList.get(position);
		ItemViewHolder holder = (ItemViewHolder) rawHolder;

		holder.titleTv.setText(item.getTitleText());
		holder.contentTv.setText(item.getContentText());
	}

	@Override
	public int getItemCount() {
		return mDataList == null ? 0 : mDataList.size();
	}

	static class ItemViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.text1)
		TextView titleTv;
		@BindView(R.id.text2)
		TextView contentTv;

		ItemViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}