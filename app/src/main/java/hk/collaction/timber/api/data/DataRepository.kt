package hk.collaction.timber.api.data

import hk.collaction.timber.api.core.ApiManager
import hk.collaction.timber.api.response.LikeTreeResponse
import hk.collaction.timber.api.response.TreeListResponse

class DataRepository(private val apiManager: ApiManager) : BaseRepository() {

    suspend fun getTreeList(
        type: String?,
        lang: String?,
        treeId: String? = null
    ): TreeListResponse {
        return apiManager.apiService.getTreeList(
            type,
            lang,
            treeId
        )
    }

    suspend fun likeTree(
        treeId: String?
    ): LikeTreeResponse {
        return apiManager.apiService.likeTree(
            treeId,
        )
    }
}