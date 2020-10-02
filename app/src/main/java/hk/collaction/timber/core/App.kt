package hk.collaction.timber.core

import android.app.Application
import android.os.Build
import com.blankj.utilcode.util.Utils
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import hk.collaction.timber.BuildConfig
import hk.collaction.timber.api.core.ApiManager
import hk.collaction.timber.api.data.DataRepository
import hk.collaction.timber.ui.main.MainViewModel
import hk.collaction.timber.ui.tree.TreeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import java.util.ArrayList

/**
 * Created by himphen on 24/5/16.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // init logger
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        Utils.init(this)

        // dependency injection
        initKoin()

        // init Crashlytics
        initCrashlytics()
    }

    private fun initCrashlytics() {
        var isGooglePlay = false

        val allowedPackageNames = ArrayList<String>()
        allowedPackageNames.add("com.android.vending")
        allowedPackageNames.add("com.google.android.feedback")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            packageManager.getInstallSourceInfo(packageName).initiatingPackageName?.let { initiatingPackageName ->
                isGooglePlay = allowedPackageNames.contains(initiatingPackageName)
            }
        } else {
            @Suppress("DEPRECATION")
            packageManager.getInstallerPackageName(packageName)?.let { installerPackageName ->
                isGooglePlay = allowedPackageNames.contains(installerPackageName)
            }
        }

        if (isGooglePlay || BuildConfig.DEBUG) {
            val crashlytics = FirebaseCrashlytics.getInstance()
            crashlytics.setCrashlyticsCollectionEnabled(true)
            crashlytics.setCustomKey("isGooglePlay", isGooglePlay)
        }
    }


    // dependency injection
    private val appModule = module {
        // singleton service
        single { SharedPreferencesManager(this@App) }
        single { ApiManager(this@App) }
        // single instance of Repository
        single { DataRepository(get()) }
        // ViewModels
        viewModel { TreeViewModel(get()) }
        viewModel { MainViewModel(get()) }
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(appModule)
        }
    }
}