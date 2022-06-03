package id.rafli.mystore.helper

import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(){

    lateinit var root: View
    var toast = HelperToast()
    
}