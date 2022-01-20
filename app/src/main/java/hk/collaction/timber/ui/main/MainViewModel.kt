package hk.collaction.timber.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hk.collaction.timber.api.data.DataRepository
import hk.collaction.timber.api.model.Tree
import hk.collaction.timber.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val repo: DataRepository) : BaseViewModel() {

    val trees = MutableLiveData<List<Tree>>()

    fun getTreeList(lang: String?) {
        viewModelScope.launch {
            try {
                val treeList = repo.getTreeList(
                    "all",
                    lang,
                    null
                ).data?.trees ?: emptyList()

                trees.postValue(treeList)
            } catch (e: Exception) {
                trees.postValue(emptyList())
            }
        }
    }

    fun likeTree(treeId: String) {
        viewModelScope.launch {
            repo.likeTree(treeId)
        }
    }
}
