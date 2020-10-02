package hk.collaction.timber.api.data

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


abstract class BaseRepository {
    val disposables = CompositeDisposable()
    val sIsLoading = PublishSubject.create<Boolean>()
    val sError = PublishSubject.create<Throwable>()

    fun dispose() {
        disposables.dispose()
    }

    /**
     * 统一线程处理
     *c
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerHelper(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                .doOnSubscribe { sIsLoading.onNext(true) }
                .doOnTerminate { sIsLoading.onNext(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}

interface RepoCallback<T> {
    fun onComplete(response: T)
    fun onError(error: Throwable)
}
