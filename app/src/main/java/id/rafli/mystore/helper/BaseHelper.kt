package id.rafli.mystore.helper

//import id.rafli.mystore.network.ClientService
import id.rafli.mystore.network.ClientService
import io.reactivex.disposables.Disposable

open class BaseHelper {

    val ApiServiceServer by lazy { ClientService().create(false) }
    val ApiServiceServerFile by lazy { ClientService().create(true) }
    var disposable: Disposable? = null


}