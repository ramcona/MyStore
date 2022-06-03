package id.rafli.mystore.network

import id.rafli.mystore.model.User
import id.rafli.mystore.network.response.AuthResponse
import id.rafli.mystore.network.response.ProdukResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServiceServer {

    @GET("produk")
    fun getAllProduk(
    ): Observable<ProdukResponse>

    @Multipart
    @POST("produk/add")
    fun addProdukWithImage(
        @Part("code") code: RequestBody,
        @Part("name") name: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("price") price: RequestBody,
        @Part("stok") stok: RequestBody,
        @Part image: MultipartBody.Part
    ): Observable<ProdukResponse>

    @FormUrlEncoded
    @POST("produk/add")
    fun addProduk(
        @Field("code") code: String,
        @Field("name") name: String,
        @Field("deskripsi") deskripsi: String,
        @Field("price") price: String,
        @Field("stok") stok: String
    ): Observable<ProdukResponse>

    @Multipart
    @POST("produk/edit")
    fun editProdukWithImage(
        @Part("code") code: RequestBody,
        @Part("name") name: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("price") price: RequestBody,
        @Part("stok") stok: RequestBody,
        @Part("id") id: RequestBody,
        @Part image: MultipartBody.Part
    ): Observable<ProdukResponse>

    @FormUrlEncoded
    @POST("produk/edit")
    fun editProduk(
        @Field("code") code: String,
        @Field("name") name: String,
        @Field("deskripsi") deskripsi: String,
        @Field("price") price: String,
        @Field("stok") stok: String,
        @Field("id") id: String,
    ): Observable<ProdukResponse>

    @FormUrlEncoded
    @POST("produk/delete")
    fun deleteProduk(
        @Field("id") id: String,
    ): Observable<ProdukResponse>

    @POST("user/login")
    fun login(
        @Body data:User
    ): Observable<AuthResponse>

    @POST("user/register")
    fun register(
        @Body data:User
    ): Observable<AuthResponse>



}