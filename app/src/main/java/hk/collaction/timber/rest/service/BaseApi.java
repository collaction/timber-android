package hk.collaction.timber.rest.service;

import hk.collaction.timber.rest.model.response.LikeTreeResponse;
import hk.collaction.timber.rest.model.response.TreeListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApi {

	@GET("api_get_tree_list")
	Call<TreeListResponse> getTreeList(
			@Query("type") String type,
			@Query("lang") String lang,
			@Query("tree_id") Integer treeId);

	@POST("api_like_tree")
	Call<LikeTreeResponse> likeTree(
			@Query("tree_id") Integer treeId);
}
