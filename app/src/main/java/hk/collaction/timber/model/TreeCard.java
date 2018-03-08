package hk.collaction.timber.model;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import hk.collaction.timber.C;
import hk.collaction.timber.R;
import hk.collaction.timber.rest.model.MyCallback;
import hk.collaction.timber.rest.model.response.LikeTreeResponse;
import hk.collaction.timber.rest.service.ApiClient;
import hk.collaction.timber.ui.activity.TreeActivity;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Himphen on 28/7/2017.
 */
@Layout(R.layout.tree_card_view)
public class TreeCard {

	@View(R.id.profileImageView)
	private ImageView profileImageView;

	@View(R.id.infoLayout)
	private LinearLayout infoLayout;

	@View(R.id.nameAgeTxt)
	private TextView nameAgeTxt;

	private Tree mTree;
	private Context mContext;
	private SwipePlaceHolderView mSwipeView;

	public TreeCard(Context context, Tree tree, SwipePlaceHolderView swipeView) {
		mContext = context;
		mTree = tree;
		mSwipeView = swipeView;
	}

	@Resolve
	private void onResolved() {
		Glide.with(mContext).load(mTree.getPhotoPath().getOriginal()).into(profileImageView);
		nameAgeTxt.setText(mTree.getName());

		infoLayout.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(android.view.View v) {
				Intent intent = new Intent(mContext, TreeActivity.class);
				intent.putExtra("treeId", mTree.getId());
				mContext.startActivity(intent);
			}
		});
	}

	@SwipeOut
	private void onSwipedOut() {
//		Log.d("EVENT", "onSwipedOut" + mSwipeView.getChildCount());
		if (mSwipeView.getChildCount() == 1) {
			C.loadTimberList(mContext, mSwipeView);
		}
	}

	@SwipeCancelState
	private void onSwipeCancelState() {
//		Log.d("EVENT", "onSwipeCancelState");
	}

	@SwipeIn
	private void onSwipeIn() {
//		Log.d("EVENT", "onSwipedIn" + mSwipeView.getChildCount());

		new ApiClient()
				.getApiClient()
				.likeTree(mTree.getId())
				.enqueue(new MyCallback<LikeTreeResponse>(mContext) {
					@Override
					public void onResponse(Call<LikeTreeResponse> call, Response<LikeTreeResponse> response) {
					}

					@Override
					public void onFailure(Call<LikeTreeResponse> call, Throwable t) {
					}
				});

		if (mSwipeView.getChildCount() == 1) {
			C.loadTimberList(mContext, mSwipeView);
		}
	}

	@SwipeInState
	private void onSwipeInState() {
//		Log.d("EVENT", "onSwipeInState");
	}

	@SwipeOutState
	private void onSwipeOutState() {
//		Log.d("EVENT", "onSwipeOutState");
	}
}