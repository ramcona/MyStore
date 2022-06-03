package id.rafli.mystore.ui.produk


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.rafli.mystore.R
import id.rafli.mystore.model.Produk
import id.rafli.mystore.network.response.ProdukResponse
import id.rafli.mystore.repositories.ProdukRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ProdukViewModel : ViewModel() {
    private lateinit var subscription: Disposable
    var context:Context? = null
    private var repo = ProdukRepo()
    private val response = MutableLiveData<ProdukResponse>()
    private val responseDelete = MutableLiveData<ProdukResponse>()
    private var isLoading = MutableLiveData<Boolean>()

    private var errorMsg = MutableLiveData<String>()

    init {
        response.value = null
        responseDelete.value = null
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

    fun getResponse():LiveData<ProdukResponse>{
        return response
    }

    fun getResponseDelete():LiveData<ProdukResponse>{
        return responseDelete
    }

    fun getAll() {

        subscription = repo.get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                { result ->

                    when (result.code) {
                        1 -> {
                            response.value = result
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

    fun addProdukWithImage(produk:Produk, image: File) {
        val imageFile = image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val partImage = MultipartBody.Part.createFormData("image", image.name, imageFile)


        val partKode = produk.code.toRequestBody("text/plain".toMediaTypeOrNull())
        val partNama = produk.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val partDeskripsi = produk.deskripsi.toRequestBody("text/plain".toMediaTypeOrNull())
        val partHarga = produk.price.toRequestBody("text/plain".toMediaTypeOrNull())
        val partStok = produk.stok.toRequestBody("text/plain".toMediaTypeOrNull())

        subscription = repo.addProdukWithImage(partImage, partKode, partNama, partDeskripsi, partHarga, partStok)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                { result ->
                    when (result.code) {
                        1 -> {
                            response.value = result
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

    fun addProduk(produk:Produk) {
        subscription = repo.addProduk(produk.code, produk.name, produk.deskripsi, produk.price, produk.stok)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                { result ->
                    when (result.code) {
                        1 -> {
                            response.value = result
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

    fun editProdukWithImage(produk:Produk, image: File) {
        val imageFile = image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val partImage = MultipartBody.Part.createFormData("image", image.name, imageFile)


        val partKode = produk.code.toRequestBody("text/plain".toMediaTypeOrNull())
        val partNama = produk.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val partDeskripsi = produk.deskripsi.toRequestBody("text/plain".toMediaTypeOrNull())
        val partHarga = produk.price.toRequestBody("text/plain".toMediaTypeOrNull())
        val partStok = produk.stok.toRequestBody("text/plain".toMediaTypeOrNull())
        val partId = produk.id.toRequestBody("text/plain".toMediaTypeOrNull())

        subscription = repo.editProdukWithImage(partImage, partKode, partNama, partDeskripsi, partHarga, partStok, partId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                { result ->
                    when (result.code) {
                        1 -> {
                            response.value = result
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

    fun editProduk(produk:Produk) {
        subscription = repo.editProduk(produk.id, produk.code, produk.name, produk.deskripsi, produk.price, produk.stok)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                { result ->
                    when (result.code) {
                        1 -> {
                            response.value = result
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

    fun delete(produk:Produk) {
        subscription = repo.delete(produk.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doOnTerminate { isLoading.value = false }
            .subscribe(
                { result ->
                    when (result.code) {
                        1 -> {
                            val data = result
                            data.produk = produk
                            responseDelete.value = data
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