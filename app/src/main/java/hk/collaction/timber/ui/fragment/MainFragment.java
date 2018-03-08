package hk.collaction.timber.ui.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hk.collaction.timber.C;
import hk.collaction.timber.R;
import hk.collaction.timber.Util;
import hk.collaction.timber.ui.activity.SettingsActivity;

/**
 * Created by himphen on 21/5/16.
 */
public class MainFragment extends BaseFragment {

	@BindView(R.id.rejectBtn)
	ImageButton rejectBtn;
	@BindView(R.id.acceptBtn)
	ImageButton acceptBtn;

	@BindView(R.id.swipeView)
	SwipePlaceHolderView mSwipeView;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	@Override
	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		int bottomMargin = Utils.dpToPx(160);
		Point windowSize = Util.getDisplaySize(mContext.getWindowManager());

		mSwipeView.getBuilder()
				.setDisplayViewCount(5)
				.setSwipeDecor(new SwipeDecor()
						.setViewWidth(windowSize.x)
						.setViewHeight(windowSize.y - bottomMargin)
						.setViewGravity(Gravity.TOP)
						.setSwipeInMsgLayoutId(R.layout.tree_swipe_in_msg_view)
						.setSwipeOutMsgLayoutId(R.layout.tree_swipe_out_msg_view)
						.setRelativeScale(0.01f));

		C.loadTimberList(mContext, mSwipeView);

		rejectBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSwipeView.doSwipe(false);
			}
		});

		acceptBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSwipeView.doSwipe(true);
			}
		});
	}

	@OnClick(R.id.settingsBtn)
	void onClickSettings() {
		startActivity(new Intent(mContext, SettingsActivity.class));
	}
}
