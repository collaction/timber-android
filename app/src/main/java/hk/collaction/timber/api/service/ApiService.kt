package hk.collaction.timber.api.service

import hk.collaction.timber.api.response.LikeTreeResponseModel
import hk.collaction.timber.api.response.TreeListResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("v1.0/tree_list")
    fun getTreeList(
        @Query("type") type: String?,
        @Query("lang") lang: String?,
        @Query("tree_id") treeId: String?
    ): Observable<TreeListResponseModel?>

    @POST("v1.0/like_tree")
    fun likeTree(
        @Query("tree_id") treeId: String?
    ): Observable<LikeTreeResponseModel?>
}