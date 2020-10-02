package hk.collaction.timber.ui.tree

import androidx.lifecycle.MutableLiveData
import hk.collaction.timber.api.data.DataRepository
import hk.collaction.timber.api.model.Tree
import hk.collaction.timber.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TreeViewModel(private val repo: DataRepository) : BaseViewModel() {

    val tree = MutableLiveData<Tree>()

    fun getTree(
        lang: String?,
        treeId: String?
    ) {
        disposables.add(repo
            .getTreeList(
                "all",
                lang,
                treeId
            )
            .doOnSubscribe { sIsLoading.onNext(true) }
            .doOnTerminate { sIsLoading.onNext(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tree.value = it.data?.trees?.getOrNull(0)
            }, {
                tree.value = null
            })
        )
    }
}
