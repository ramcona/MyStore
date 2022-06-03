package id.rafli.mystore.app

import android.app.Application
import android.os.Build
import android.os.Environment
import android.os.StrictMode
import id.rafli.mystore.helper.Config.DIRECTORY_IMAGE
import id.rafli.mystore.helper.Helper
import id.rafli.mystore.helper.HelperToast
import id.rafli.mystore.model.User
import id.rafli.mystore.utilities.SharedPref
import java.io.File

class App : Application() {

    companion object {
        lateinit var pref: SharedPref
        lateinit var user: User

        var helper = Helper
        var toast = HelperToast()
        val curdate:String = java.text.SimpleDateFormat(
            "yyyy-MM-dd",
            java.util.Locale("ID")
        ).format(
            java.util.Date()
        )
    }

    fun clearAppData(){
        pref.clearAll()

        user = User()
    }



    override fun onCreate() {
        super.onCreate()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        pref = SharedPref(this)
        user = pref.getUser()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            DIRECTORY_IMAGE =  getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
            val sdIconStorageDir = File(DIRECTORY_IMAGE)
            if (!sdIconStorageDir.isDirectory) {
                //BUAT DIRECTORY
                sdIconStorageDir.mkdirs()
            }
        }
        else{
            val sdIconStorageDir = File(DIRECTORY_IMAGE)
            if (!sdIconStorageDir.isDirectory) {
                //BUAT DIRECTORY
                sdIconStorageDir.mkdirs()
            }
        }
    }

}