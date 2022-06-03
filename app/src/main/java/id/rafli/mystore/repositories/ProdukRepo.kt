package id.rafli.mystore.repositories

import id.rafli.mystore.helper.BaseHelper
import id.rafli.mystore.model.Produk
import id.rafli.mystore.network.response.ProdukResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProdukRepo: BaseHelper(){

    fun get(): Observable<ProdukResponse> {
        return ApiServiceServer.getAllProduk()
    }

    fun addProdukWithImage(image: MultipartBody.Part, kode: RequestBody, nama: RequestBody, deskripsi: RequestBody, harga: RequestBody, stok: RequestBody): Observable<ProdukResponse>{
        return  ApiServiceServerFile.addProdukWithImage(kode, nama, deskripsi, harga, stok, image)
    }

    fun addProduk(kode: String, nama: String, deskripsi: String, harga: String, stok: String): Observable<ProdukResponse>{
        return  ApiServiceServer.addProduk(kode, nama, deskripsi, harga, stok)
    }

    fun editProdukWithImage(image: MultipartBody.Part, kode: RequestBody, nama: RequestBody, deskripsi: RequestBody, harga: RequestBody, stok: RequestBody, id: RequestBody): Observable<ProdukResponse>{
        return  ApiServiceServerFile.editProdukWithImage(kode, nama, deskripsi, harga, stok, id, image)
    }

    fun editProduk(id: String, kode: String, nama: String, deskripsi: String, harga: String, stok: String): Observable<ProdukResponse>{
        return  ApiServiceServer.editProduk(kode, nama, deskripsi, harga, stok, id)
    }

    fun delete(id:String): Observable<ProdukResponse>{
        return ApiServiceServer.deleteProduk(id)
    }

}