package hk.collaction.timber.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import hk.collaction.timber.utils.Utils
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    var disposables = CompositeDisposable()

    protected fun isPermissionsGranted(permissions: Array<String>): Boolean {
        return Utils.isPermissionsGranted(context, permissions)
    }

    protected fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        return Utils.hasAllPermissionsGranted(grantResults)
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initEvent()
    }

    open fun initEvent() {}
}