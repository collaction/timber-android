package hk.collaction.timber.rest.model;

import android.content.Context;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by himphen on 18/5/16.
 */
public class MyCallback<BaseResponse> implements Callback<BaseResponse> {

	public WeakReference<Context> mContextReference;

	public MyCallback(Context context) {
		mContextReference = new WeakReference<>(context);
	}

	@Override
	public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

	}

	@Override
	public void onFailure(Call<BaseResponse> call, Throwable t) {

	}
}