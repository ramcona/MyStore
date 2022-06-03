package id.rafli.mystore.network

import android.util.Log
import id.rafli.mystore.helper.Config.BASE_API
import id.rafli.mystore.helper.Validasi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ClientService {

    fun create(file: Boolean): ApiServiceServer {
        var cTO:Long = 30
        var wTO:Long = 30
        var rTO:Long = 30

        if (file){
            cTO = 120
            wTO = 120
            rTO = 120
        }

        val interceptor = HttpLoggingInterceptor(Logger())
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient: OkHttpClient = if (Validasi().isRelease()) {
            Log.e("TEST", "rilis")
            OkHttpClient.Builder()
                .connectTimeout(cTO, TimeUnit.SECONDS)
                .writeTimeout(wTO, TimeUnit.SECONDS)
                .readTimeout(rTO, TimeUnit.SECONDS)
                .build()
        } else {
            Log.e("TEST", "sandbox")
            OkHttpClient.Builder()
                .addInterceptor(interceptor) //iterceptor hanya untuk debug link tidak untuk release
                .connectTimeout(cTO, TimeUnit.SECONDS)
                .writeTimeout(wTO, TimeUnit.SECONDS)
                .readTimeout(rTO, TimeUnit.SECONDS)
                .build()
        }


        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(BASE_API)
            .client(okHttpClient)
            .build()


        return retrofit.create(ApiServiceServer::class.java)
    }


    /*UNTUK UPLOAD FILE*/
    private var retrofit: Retrofit? = null
    fun getClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY


        val okHttpClient  = OkHttpClient.Builder()
            .connectTimeout(150, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .addInterceptor(interceptor) //iterceptor hanya untuk debug link tidak untuk release
            .build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()

                .baseUrl(BASE_API)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

}