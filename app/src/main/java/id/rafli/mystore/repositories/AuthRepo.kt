package id.rafli.mystore.repositories

import id.rafli.mystore.helper.BaseHelper
import id.rafli.mystore.model.User
import id.rafli.mystore.network.response.AuthResponse
import io.reactivex.Observable

class AuthRepo: BaseHelper(){

    fun login(user:User): Observable<AuthResponse>{
        return ApiServiceServer.login(user)
    }

    fun register(user:User): Observable<AuthResponse>{
        return ApiServiceServer.register(user)
    }

}