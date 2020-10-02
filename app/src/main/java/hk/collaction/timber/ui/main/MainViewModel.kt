package hk.collaction.timber.ui.main

import androidx.lifecycle.MutableLiveData
import hk.collaction.timber.api.data.DataRepository
import hk.collaction.timber.api.model.Tree
import hk.collaction.timber.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val repo: DataRepository) : BaseViewModel() {

    val trees = MutableLiveData<List<Tree>>()

    fun getTreeList(
        lang: String?
    ) {
        disposables.add(repo
            .getTreeList(
                "all",
                lang,
                null
            )
            .doOnSubscribe { sIsLoading.onNext(true) }
            .doOnTerminate { sIsLoading.onNext(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                trees.value = it.data?.trees
            }, {
                trees.value = emptyList()
            })
        )
    }

    fun likeTree(treeId: String) {
        disposables.add(repo
            .likeTree(treeId)
            .doOnSubscribe { sIsLoading.onNext(true) }
            .doOnTerminate { sIsLoading.onNext(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
        )
    }
}
