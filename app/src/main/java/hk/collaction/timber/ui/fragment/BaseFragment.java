package hk.collaction.timber.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import hk.collaction.timber.R;

public class BaseFragment extends Fragment {

	protected Activity mContext;
	protected MaterialDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		progressDialog = showIndeterminateProgressDialog(false);
	}

	private MaterialDialog showIndeterminateProgressDialog(boolean horizontal) {
		return new MaterialDialog.Builder(mContext)
				.content(R.string.ui_loading_message)
				.progress(true, 0)
				.cancelable(false)
				.progressIndeterminateStyle(horizontal)
				.build();
	}

	protected Snackbar initSnackbar(Snackbar snackbar) {
		View sbView = snackbar.getView();
		sbView.setBackgroundResource(R.color.primary_dark);
		((TextView) sbView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
		snackbar.show();
		return snackbar;
	}
}
