package id.rafli.mystore.helper

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import id.rafli.mystore.R
import id.rafli.mystore.databinding.LayoutToolbarBinding
import java.text.SimpleDateFormat
import java.util.*


open class BaseActivity : AppCompatActivity() {

    var toast = HelperToast()

    val curdate:String = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss",
        Locale("ID")
    ).format(
        Date()
    )

    val curDay:String = SimpleDateFormat(
        "d",
        Locale("ID")
    ).format(
        Date()
    )

    val curdateonly:String = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale("ID")
    ).format(
        Date()
    )

    val curtime:String = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss",
        Locale("ID")
    ).format(
        Date()
    )


    fun openTwitter(username:String){
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?screen_name=$username")
                )
            )
        } catch (e: Exception) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/#!/$username")
                )
            )
        }
    }

    fun openTikTokProfile(username:String) {
        val uri: Uri = Uri.parse("https://www.tiktok.com/${username}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.zhiliaoapp.musically")


        try {
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }else{
                val forBrowser = Intent(Intent.ACTION_VIEW, uri)
                startActivity(forBrowser)
            }
        } catch (e: ActivityNotFoundException) {
            val forBrowser = Intent(Intent.ACTION_VIEW, uri)
            startActivity(forBrowser)
        }

    }

    fun launchInsta(uname:String) {
        val uriForApp: Uri = Uri.parse("http://instagram.com/_u/${uname}")
        val forApp = Intent(Intent.ACTION_VIEW, uriForApp)

        val uriForBrowser: Uri = Uri.parse("http://instagram.com/${uname}")
        val forBrowser = Intent(Intent.ACTION_VIEW, uriForBrowser)

        forApp.component =
            ComponentName(
                "com.instagram.android",
                "com.instagram.android.activity.UrlHandlerActivity"
            )

        try {
            startActivity(forApp)
        } catch (e: ActivityNotFoundException) {
            startActivity(forBrowser)
        }
    }

    fun statusPutih() {
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window: Window = window
            val decorView: View = window.decorView
            val wic = WindowInsetsControllerCompat(window, decorView)


            when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    wic.isAppearanceLightStatusBars = false // true or false as desired.
                    window.statusBarColor = Color.BLACK
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    wic.isAppearanceLightStatusBars = true // true or false as desired.
                    window.statusBarColor = Color.WHITE
                }
                Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    wic.isAppearanceLightStatusBars = true // true or false as desired.
                    window.statusBarColor = Color.WHITE
                }
            }

        }


    }


    fun setToolbar(title: String, toolbarTitle: LayoutToolbarBinding) {
        statusPutih()

        when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                toolbarTitle.toolbarBack.setImageResource(R.drawable.ic_back_white)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                toolbarTitle.toolbarBack.setImageResource(R.drawable.ic_back_black)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                toolbarTitle.toolbarBack.setImageResource(R.drawable.ic_back_black)
            }
        }

        toolbarTitle.toolbarTitle.text = title
        toolbarTitle.toolbarBack.setOnClickListener {
            onBackPressed()
        }

        //mengubah warna status bar

    }

    fun failureRequest(context: Activity, msg: String){

        SweetAlert.dismis()
        SweetAlert.onFailure(context, msg)

    }

    fun pindah(tujuan: Context, context: Context){

        val intent = Intent(context, tujuan::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }

}