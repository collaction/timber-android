package hk.collaction.timber.rest.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Himphen on 23/8/2017.
 */

public class TreeListWrapper extends BaseWrapper {
	@SerializedName("type")
	public String type;

	@SerializedName("lang")
	public String lang;

	@SerializedName("tree_id")
	public Integer treeId;
}

