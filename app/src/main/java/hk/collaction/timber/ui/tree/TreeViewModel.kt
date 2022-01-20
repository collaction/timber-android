package hk.collaction.timber.ui.tree

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.himphen.logger.Logger
import hk.collaction.timber.api.data.DataRepository
import hk.collaction.timber.api.model.Tree
import hk.collaction.timber.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TreeViewModel(private val repo: DataRepository) : BaseViewModel() {

    val treeLiveData = MutableLiveData<Tree?>()

    fun getTree(
        lang: String?,
        treeId: String?
    ) {
        viewModelScope.launch {
            try {
                val tree = repo.getTreeList(
                    "all",
                    lang,
                    treeId
                ).data?.trees?.getOrNull(0)

                treeLiveData.postValue(tree)
            } catch (e: Exception) {
                Logger.e(e, "net error")
                treeLiveData.postValue(null)
            }
        }
    }
}
