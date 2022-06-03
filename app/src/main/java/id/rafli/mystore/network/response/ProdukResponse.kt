package id.rafli.mystore.network.response

import id.rafli.mystore.model.Produk
import java.io.Serializable

class ProdukResponse : Serializable {

    val error:Boolean  = false
    val code:Int = 0
    val message:String = ""
    val produks:List<Produk> = ArrayList()
    var produk:Produk = Produk()

}