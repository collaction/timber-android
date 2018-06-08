package hk.collaction.timber.model;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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
import hk.collaction.timber.rest.model.BaseCallback;
import hk.collaction.timber.rest.model.request.TreeListWrapper;
import hk.collaction.timber.rest.model.response.LikeTreeResponse;
import hk.collaction.timber.rest.model.response.TreeListResponse;
import hk.collaction.timber.rest.service.BaseApiClient;
import hk.collaction.timber.ui.activity.TreeActivity;
import hk.collaction.timber.ui.view.TreeSwipeView;
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
	private TreeSwipeView mSwipeView;

	public TreeCard(Context context, Tree tree, TreeSwipeView swipeView) {
		mContext = context;
		mTree = tree;
		mSwipeView = swipeView;
	}

	@Resolve
	private void onResolved() {
		Glide.with(mContext)
				.load(mTree.getPhotoPath().getOriginal())
				.apply(new RequestOptions()
						.diskCacheStrategy(DiskCacheStrategy.ALL))
				.into(profileImageView);
		nameAgeTxt.setText(mTree.getName());

		infoLayout.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(android.view.View v) {
				Intent intent = new Intent(mContext, TreeActivity.class);
				intent.putExtra(C.ARG_TREE_ID, mTree.getId());
				mContext.startActivity(intent);
			}
		});
	}

	@SwipeOut
	private void onSwipedOut() {
		if (mSwipeView.getChildCount() == 1) {
			mSwipeView.loadTimberList();
		}
	}

	@SwipeCancelState
	private void onSwipeCancelState() {
	}

	@SwipeIn
	private void onSwipeIn() {
		new BaseApiClient()
				.getApiClient()
				.likeTree(mTree.getId())
				.enqueue(new BaseCallback<LikeTreeResponse>(mContext) {
					@Override
					public void onResponse(Call<LikeTreeResponse> call, Response<LikeTreeResponse> response) {
					}

					@Override
					public void onFailure(Call<LikeTreeResponse> call, Throwable t) {
					}
				});

		if (mSwipeView.getChildCount() == 1) {
			mSwipeView.loadTimberList();
		}
	}

	@SwipeInState
	private void onSwipeInState() {
	}

	@SwipeOutState
	private void onSwipeOutState() {
	}
}