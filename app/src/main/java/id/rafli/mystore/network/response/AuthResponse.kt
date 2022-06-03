package id.rafli.mystore.network.response

import id.rafli.mystore.model.Produk
import id.rafli.mystore.model.User
import java.io.Serializable

class AuthResponse : Serializable {

    val error:Boolean  = false
    val code:Int = 0
    val message:String = ""
    var user:User = User()

}