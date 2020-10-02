package hk.collaction.timber.ui.base

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import hk.collaction.timber.R
import hk.collaction.timber.utils.Utils.updateLanguage
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : AppCompatActivity() {
    open var containerView = R.layout.activity_container

    var disposables = CompositeDisposable()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(updateLanguage(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEvent()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected fun initActionBar(
        toolbar: Toolbar?,
        titleString: String? = null, subtitleString: String? = null,
        @StringRes titleId: Int? = null, @StringRes subtitleId: Int? = null
    ) {
        toolbar?.let {
            setSupportActionBar(toolbar)
            supportActionBar?.let { ab ->
                ab.setDisplayHomeAsUpEnabled(true)
                ab.setHomeButtonEnabled(true)
                titleString?.let {
                    ab.title = titleString
                }
                titleId?.let {
                    ab.setTitle(titleId)
                }
                subtitleString?.let {
                    ab.subtitle = subtitleString
                }
                subtitleId?.let {
                    ab.setSubtitle(subtitleId)
                }
            }
        }
    }

    fun initFragment(fragment: Fragment?, titleString: String?, titleId: Int?) {
        fragment?.let {
            setContentView(containerView)
            initActionBar(toolbar, titleString = titleString, titleId = titleId)

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }

    open fun initEvent() {}

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}