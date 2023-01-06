package id.rafli.mystore.ui.login


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.rafli.mystore.R
import id.rafli.mystore.app.App
import id.rafli.mystore.app.App.Companion.pref
import id.rafli.mystore.model.Produk
import id.rafli.mystore.model.User
import id.rafli.mystore.network.response.AuthResponse
import id.rafli.mystore.network.response.ProdukResponse
import id.rafli.mystore.repositories.AuthRepo
import id.rafli.mystore.repositories.ProdukRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AuthViewModel : ViewModel() {
    private lateinit var subscription: Disposable
    var context:Context? = null
    private var repo = AuthRepo()
    private val response = MutableLiveData<AuthResponse>()

    private var isLoading = MutableLiveData<Boolean>()

    private var errorMsg = MutableLiveData<String>()

    init {
        response.value = null
    }

    override fun onCleared() {
        super.onCleared()

        try{
            subscription.dispose()
        }catch (e: UninitializedPropertyAccessException){

        }

    }

    fun getLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun getError():LiveData<String>{
        return errorMsg
    }

    fun getResponse():LiveData<AuthResponse>{
        return response
    }


    fun login(user: User) {

        subscription = repo.login(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                { result ->

                    when (result.code) {
                        1 -> {
                            response.value = result
                            pref.setIsLogin(true)
                            pref.setUser(result.user)
                            App.user = result.user
                        }
                        else -> {
                            errorMsg.value = result.message
                        }
                    }

                },
                { error ->
                    try{

                        when {
                            error.message!!.contains("Failed to connect", true) -> {
                                errorMsg.value = context?.getString(R.string.teks_tidak_dapat_tehubung_ke_server)
                            }
                            error.message.toString().contains("4") -> {
                                errorMsg.value = context?.getString(R.string.teks_terjadi_kesalahan_code, 4000)
                            }
                            error.message.toString().contains("5") -> {
                                errorMsg.value = context?.getString(R.string.teks_terjadi_kesalahan_code, 5000)
                            }
                            else -> {
                                errorMsg.value = context?.getString(R.string.teks_terjadi_kesalahan_deskripsi)
                            }
                        }
                    }catch (e:KotlinNullPointerException){
                        errorMsg.value = "Terjadi kesalahan saat memproses data. coba beberapa saat lagi. 2003"
                    }
                }

            )
    }

    fun register(user: User) {

        subscription = repo.register(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                { result ->

                    when (result.code) {
                        1 -> {
                            response.value = result
                            pref.setIsLogin(true)
                            pref.setUser(result.user)
                            App.user = result.user
                        }
                        else -> {
                            errorMsg.value = result.message
                        }
                    }

                },
                { error ->
                    try{

                        when {
                            error.message!!.contains("Failed to connect", true) -> {
                                errorMsg.value = context?.getString(R.string.teks_tidak_dapat_tehubung_ke_server)
                            }
                            error.message.toString().contains("4") -> {
                                errorMsg.value = context?.getString(R.string.teks_terjadi_kesalahan_code, 4000)
                            }
                            error.message.toString().contains("5") -> {
                                errorMsg.value = context?.getString(R.string.teks_terjadi_kesalahan_code, 5000)
                            }
                            else -> {
                                errorMsg.value = context?.getString(R.string.teks_terjadi_kesalahan_deskripsi)
                            }
                        }
                    }catch (e:KotlinNullPointerException){
                        errorMsg.value = "Terjadi kesalahan saat memproses data. coba beberapa saat lagi. 2003"
                    }
                }

            )
    }

}