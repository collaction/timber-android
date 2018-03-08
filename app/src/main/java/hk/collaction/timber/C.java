package hk.collaction.timber;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.io.IOException;

import hk.collaction.timber.model.Tree;
import hk.collaction.timber.model.TreeCard;
import hk.collaction.timber.rest.model.MyCallback;
import hk.collaction.timber.rest.model.request.TreeListWrapper;
import hk.collaction.timber.rest.model.response.TreeListResponse;
import hk.collaction.timber.rest.service.ApiClient;
import retrofit2.Call;
import retrofit2.Response;

public class C extends Util {

	public static final String TAG = "TAG";

	public static void loadTimberList(Context mContext, final SwipePlaceHolderView mSwipeView) {
		TreeListWrapper wrapper = new TreeListWrapper();
		wrapper.type = "basic";
		wrapper.lang = mContext.getString(R.string.ui_lang);

		new ApiClient()
				.getApiClient()
				.getTreeList(
						wrapper.type,
						wrapper.lang,
						null
				)
				.enqueue(new MyCallback<TreeListResponse>(mContext) {
					@Override
					public void onResponse(Call<TreeListResponse> call, Response<TreeListResponse> response) {
						if (mContextReference.get() == null) {
							return;
						}

						if (response.isSuccessful()) {
							for (Tree tree : response.body().trees) {
								mSwipeView.addView(new TreeCard(mContextReference.get(), tree, mSwipeView));
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


	public static void errorDialog(Context context, Response response) {
		String errorMessage = context.getString(R.string.ui_fatal_error);
		try {
			if (response.code() == 401) {
				return;
			} else {
				errorMessage = response.errorBody().string();
			}
		} catch (IOException ignored) {
		}
		errorDialog(context, errorMessage);
	}

	public static void errorDialog(Context context, Throwable throwable) {
		throwable.printStackTrace();
		String errorMessage = throwable.getMessage();
		errorDialog(context, errorMessage);
	}

	public static void errorDialog(Context context, String errorMessage) {
		if (context == null) {
			return;
		}
		MaterialDialog.Builder builder =
				new MaterialDialog.Builder(context)
						.title(R.string.ui_sorry)
						.content(errorMessage)
						.positiveText(R.string.ui_nvm);
		builder.show();
	}
}
