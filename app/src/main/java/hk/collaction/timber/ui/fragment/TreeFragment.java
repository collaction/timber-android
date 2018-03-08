package hk.collaction.timber.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hk.collaction.timber.C;
import hk.collaction.timber.R;
import hk.collaction.timber.model.InfoItem;
import hk.collaction.timber.model.Tree;
import hk.collaction.timber.rest.model.MyCallback;
import hk.collaction.timber.rest.model.request.TreeListWrapper;
import hk.collaction.timber.rest.model.response.TreeListResponse;
import hk.collaction.timber.rest.service.ApiClient;
import hk.collaction.timber.ui.adapter.InfoItemAdapter;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Himphen on 23/8/2017.
 */

public class TreeFragment extends BaseFragment {

	@BindView(R.id.rvlist)
	RecyclerView recyclerView;

	private Integer treeId;
	private Tree mTree = null;

	public static TreeFragment newInstance(Integer treeId) {
		TreeFragment fragment = new TreeFragment();
		Bundle args = new Bundle();
		args.putInt("treeId", treeId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tree, container, false);
		ButterKnife.bind(this, rootView);
		return rootView;
	}

	@Override
	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		recyclerView.setLayoutManager(
				new LinearLayoutManager(mContext,
						LinearLayoutManager.VERTICAL, false)
		);

		treeId = getArguments().getInt("treeId");

		TreeListWrapper wrapper = new TreeListWrapper();
		wrapper.type = "all";
		wrapper.lang = getString(R.string.ui_lang);
		wrapper.treeId = treeId;

		new ApiClient()
				.getApiClient()
				.getTreeList(
						wrapper.type,
						wrapper.lang,
						wrapper.treeId
				)
				.enqueue(new MyCallback<TreeListResponse>(mContext) {
					@Override
					public void onResponse(Call<TreeListResponse> call, Response<TreeListResponse> response) {
						if (mContextReference.get() == null) {
							return;
						}

						if (response.isSuccessful()) {
							if (response.body().trees.size() > 0) {
								mTree = response.body().trees.get(0);
								init();
							} else {
								// error
							}
						} else {
							C.errorDialog(mContext, response);
						}
					}

					@Override
					public void onFailure(Call<TreeListResponse> call, Throwable t) {
						C.errorDialog(mContext, t);
					}
				});
	}

	private void init() {
		FragmentManager fm = getFragmentManager();
		LatLng mLatLng = new LatLng(mTree.getLat(), mTree.getLng());

		CameraPosition cp = new CameraPosition.Builder()
				.target(mLatLng)
				.zoom(15f)
				.build();
		SupportMapFragment fragment = SupportMapFragment.newInstance(new GoogleMapOptions().camera(cp));
		fm.beginTransaction().replace(R.id.mapFragment, fragment).commit();

		fragment.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(GoogleMap googleMap) {
				LatLng mLatLng = new LatLng(mTree.getLat(), mTree.getLng());
				googleMap.addMarker(new MarkerOptions()
						.position(mLatLng)
						.title(mTree.getName())
						.snippet(mTree.getAddress())
				);

//				googleMap.getUiSettings().setScrollGesturesEnabled(false);
			}
		});

		List<InfoItem> list = new ArrayList<>();
		String[] stringArray = getResources().getStringArray(R.array.tree_info_array);

		for (int i = 0, size = stringArray.length; i < size; i++) {
			list.add(new InfoItem(stringArray[i], getData(i)));
		}

		recyclerView.setAdapter(new InfoItemAdapter(list, "horizontal"));
	}

	private String getData(int j) {
		switch (j) {
			case 0:
				return mTree.getAddress();
			case 1:
				return mTree.getDistrict();
			case 2:
				return mTree.getLat() + ", " + mTree.getLng();
			case 3:
				return mTree.getName();
			case 4:
				return mTree.getCondition();
			case 5:
				return mTree.getCode();
			case 6:
				return mTree.getRefNo();
			case 7:
				return mTree.getOvtNo();
			case 8:
				return mTree.getUpdatedAt();
			default:
				return "N/A";
		}
	}

	@OnClick(R.id.directionButton)
	void onClickDirection() {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + mTree.getLat() + "," + mTree.getLng()));
		startActivity(intent);
	}

	@OnClick(R.id.reportButton)
	void onClickReport() {
		String meta = "Android Version: " + Build.VERSION.RELEASE + "\n";
		meta += "SDK Level: " + Build.VERSION.SDK_INT + "\n";
		meta += "Version: " + C.getCurrentVersionName(mContext) + "\n";
		meta += "Brand: " + Build.BRAND + "\n";
		meta += "Model: " + Build.MODEL + "\n";
		meta += "Tree: " + mTree.toString() + "\n\n\n";

		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setData(Uri.parse("mailto:"));
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"team@collaction.hk"});
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.pref_report_title));
		intent.putExtra(Intent.EXTRA_TEXT, meta);
		startActivity(Intent.createChooser(intent, getString(R.string.pref_report_title)));
	}
}
