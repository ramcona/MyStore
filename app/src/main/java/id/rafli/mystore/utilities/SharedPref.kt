package id.rafli.mystore.utilities

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import id.rafli.mystore.model.User

class SharedPref(context : Context) {

    private val mypref = "MAIN_PREF"
    private val sp: SharedPreferences = context.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sp.edit()
    private val onReview = "onReview"
    private val isLogin = "isLogin"
    private val showRating = "showRating"
    private val openApp = "openApp"

    private val songPlay = "songPlay"

    private val notifShowroom = "notifShowroom"
    private val notifNews = "notifNews"
    private val notifMng = "notifMng"
    private val notifEvent = "notifEvent"
    private val notifHandshake = "notifHandshake"

    private val token = "token"
    private val users = "users"

    fun setOnReview(value: Boolean) {
        editor.putBoolean(onReview, value)
        editor.commit()
        editor.apply()
    }


    fun setUser(data: User): User {
        val json = Gson().toJson(data, User::class.java)
        sp.edit().putString(users, json).apply()
        return getUser()
    }

    fun getUser(): User {
        val data = sp.getString(users, null) ?: return User()
        return Gson().fromJson(data, User::class.java)
    }

    fun getOnReview(): Boolean {
        return sp.getBoolean(onReview, false)
    }

    fun setOpenApp(value : Int){
        sp.edit().putInt(openApp, value).apply()
    }

    fun setIsLogin(value : Boolean){
        sp.edit().putBoolean(isLogin, value).apply()
    }

    fun getIsLogin(): Boolean {
        return sp.getBoolean(isLogin, false)
    }

    fun getNotifShowroom(): Boolean {
        return sp.getBoolean(notifShowroom, true)
    }

    fun setNotifShowroom(value : Boolean){
        sp.edit().putBoolean(notifShowroom, value).apply()
    }

    fun getNotifNews(): Boolean {
        return sp.getBoolean(notifNews, true)
    }

    fun setNotifNews(value : Boolean){
        sp.edit().putBoolean(notifNews, value).apply()
    }

    fun getNotifMng(): Boolean {
        return sp.getBoolean(notifMng, true)
    }

    fun setNotifMng(value : Boolean){
        sp.edit().putBoolean(notifMng, value).apply()
    }

    fun getNotifEvent(): Boolean {
        return sp.getBoolean(notifEvent, true)
    }

    fun setNotifEvent(value : Boolean){
        sp.edit().putBoolean(notifEvent, value).apply()
    }

    fun getNotifHandshake(): Boolean {
        return sp.getBoolean(notifHandshake, true)
    }

    fun setNotifHandshake(value : Boolean){
        sp.edit().putBoolean(notifHandshake, value).apply()
    }

    fun getOpenApp() : Int{
        return sp.getInt(openApp, 0)
    }

    fun setRated(value : Boolean){
        sp.edit().putBoolean(showRating, value).apply()
    }

    fun isRated() : Boolean{
        return sp.getBoolean(showRating, false)
    }

    fun clearAll() {
        editor.clear()
        editor.commit()
    }

}