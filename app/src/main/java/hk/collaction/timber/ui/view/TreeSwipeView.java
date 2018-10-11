package hk.collaction.timber.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import com.mindorks.placeholderview.SwipePlaceHolderView;

import hk.collaction.timber.C;
import hk.collaction.timber.model.Tree;
import hk.collaction.timber.model.TreeCard;
import hk.collaction.timber.rest.model.BaseCallback;
import hk.collaction.timber.rest.model.request.TreeListWrapper;
import hk.collaction.timber.rest.model.response.TreeListResponse;
import hk.collaction.timber.rest.service.BaseApiClient;
import retrofit2.Call;
import retrofit2.Response;

public class TreeSwipeView extends SwipePlaceHolderView {

	public TreeSwipeView mTreeSwipeView;
	public Context mContext;

	public TreeSwipeView(Context context) {
		super(context);
		mTreeSwipeView = this;
		mContext = context;
	}

	public TreeSwipeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTreeSwipeView = this;
		mContext = context;
	}

	public TreeSwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mTreeSwipeView = this;
		mContext = context;
	}

	public void loadTimberList() {
		TreeListWrapper wrapper = new TreeListWrapper();
		wrapper.type = "basic";
		wrapper.lang = C.getCurrentLanguage(mContext);

		new BaseApiClient()
				.getApiClient()
				.getTreeList(
						wrapper.type,
						wrapper.lang,
						null
				)
				.enqueue(new BaseCallback<TreeListResponse>(mContext) {
					@Override
					public void onResponse(Call<TreeListResponse> call, Response<TreeListResponse> response) {
						if (mContextReference.get() == null) {
							return;
						}

						if (response.isSuccessful() && response.body() != null) {
							for (Tree tree : response.body().trees) {
								mTreeSwipeView.addView(new TreeCard(mContextReference.get(), tree, mTreeSwipeView));
							}

						} else {
							C.errorDialog(mContextReference.get(), response);
						}
					}

					@Override
					public void onFailure(Call<TreeListResponse> call, Throwable t) {
						C.errorDialog(mContextReference.get(), t);
					}
				});
	}

}
