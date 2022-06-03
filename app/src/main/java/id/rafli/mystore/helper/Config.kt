package id.rafli.mystore.helper

import android.os.Environment

object Config {

    const val BASE_URL = "http://mobile.arira.my.id/"
    const val BASE_API = BASE_URL + ""
    const val BASE_STORAGE = BASE_URL + ""


    const val BERHASIL = "Berhasil"

    val extra_model: String = "extraModel"
    val extra_type: String = "extraType"
    val extra_other:String = "extraOther"
    val extra_url: String = "extraUrl"
    val extra_list: String = "extraList"
    val extra_id: String = "extraID"

    var DIRECTORY_IMAGE: String = Environment.getExternalStorageDirectory().toString() + "/Wiring/redmark/"


}