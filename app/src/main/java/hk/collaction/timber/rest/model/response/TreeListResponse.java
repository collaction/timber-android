package hk.collaction.timber.rest.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import hk.collaction.timber.model.Tree;

public class TreeListResponse extends BaseResponse {

	@SerializedName("trees")
	public ArrayList<Tree> trees;
}
