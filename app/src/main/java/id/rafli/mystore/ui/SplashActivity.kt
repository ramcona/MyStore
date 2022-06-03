package id.rafli.mystore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import id.rafli.mystore.R
import id.rafli.mystore.app.App.Companion.pref
import id.rafli.mystore.helper.BaseActivity
import id.rafli.mystore.ui.login.LoginActivity
import id.rafli.mystore.utilities.Go

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hideSystemBars()

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            if (pref.getIsLogin()){
                Go(this).move(MainActivity::class.java, clearPrevious = true)
            }else{
                Go(this).move(LoginActivity::class.java, clearPrevious = true)
            }

        }, 3000)
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }
}