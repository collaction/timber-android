package hk.collaction.timber.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject


abstract class BaseViewModel : ViewModel() {

    val disposables = CompositeDisposable()
    val sIsLoading = PublishSubject.create<Boolean>()
    val sError = PublishSubject.create<Throwable>()

    open fun onCreate() {
        // override it
    }

    open fun onViewCreated() {
        // override it
    }

    open fun onDestroy() {
        disposables.dispose()
    }
}
