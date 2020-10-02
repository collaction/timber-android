package hk.collaction.timber.ui.launcher

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import hk.collaction.timber.R
import hk.collaction.timber.ui.main.MainActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        checkAppVersion()
    }

    private fun checkAppVersion() {
        Handler(Looper.getMainLooper()).postDelayed(
            { goToNextActivity() },
            2500
        )
    }

    private fun goToNextActivity() {
        val mainIntent = Intent(this@LauncherActivity, MainActivity::class.java)
        startActivity(mainIntent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onBackPressed() {}
}