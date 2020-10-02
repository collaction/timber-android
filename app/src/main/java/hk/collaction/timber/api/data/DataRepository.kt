package hk.collaction.timber.api.data

import hk.collaction.timber.api.core.ApiManager
import hk.collaction.timber.api.response.LikeTreeResponseModel
import hk.collaction.timber.api.response.TreeListResponseModel
import io.reactivex.Observable

class DataRepository(private val apiManager: ApiManager) : BaseRepository() {

    fun getTreeList(
        type: String?,
        lang: String?,
        treeId: String?
    ): Observable<TreeListResponseModel> {
        return apiManager.apiService.getTreeList(
            type,
            lang,
            treeId
        ).compose(rxSchedulerHelper())
    }

    fun likeTree(
        treeId: String?
    ): Observable<LikeTreeResponseModel> {
        return apiManager.apiService.likeTree(
            treeId,
        ).compose(rxSchedulerHelper())
    }
}