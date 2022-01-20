package hk.collaction.timber.api.service

import hk.collaction.timber.api.response.LikeTreeResponse
import hk.collaction.timber.api.response.TreeListResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("v1.0/tree_list")
    suspend fun getTreeList(
        @Query("type") type: String?,
        @Query("lang") lang: String?,
        @Query("tree_id") treeId: String?
    ): TreeListResponse

    @POST("v1.0/like_tree")
    suspend fun likeTree(
        @Query("tree_id") treeId: String?
    ): LikeTreeResponse
}