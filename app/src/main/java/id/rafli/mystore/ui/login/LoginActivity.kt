package id.rafli.mystore.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.rafli.mystore.R
import id.rafli.mystore.databinding.ActivityLoginBinding
import id.rafli.mystore.helper.BaseActivity
import id.rafli.mystore.helper.SweetAlert
import id.rafli.mystore.helper.viewBinding
import id.rafli.mystore.model.User
import id.rafli.mystore.ui.MainActivity
import id.rafli.mystore.ui.register.RegisterActivity
import id.rafli.mystore.utilities.Go

class LoginActivity : BaseActivity() {
    private val binding by viewBinding(ActivityLoginBinding::inflate)
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        statusPutih()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[AuthViewModel::class.java]
        viewModel.context = this

        action()
        observeData()
    }

    private fun action(){
        binding.btnMasuk.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            when {
                username.isEmpty() -> {
                    toast.show("Username tidak boleh kosong", this)
                }
                password.isEmpty() -> {
                    toast.show("Password tidak boleh kosong", this)
                }
                else -> {
                    val user = User()
                    user.username = username
                    user.password = password
                    viewModel.login(user)
                }
            }
        }

        binding.tvDaftar.setOnClickListener {
            Go(this).move(RegisterActivity::class.java)
        }
    }

    private fun observeData(){
        viewModel.getLoading().observe(this){
            if(it != null){
                if (it){
                    SweetAlert.onLoading(this)
                }else{
                    SweetAlert.dismis()
                }
            }
        }

        viewModel.getResponse().observe(this){
            if (it != null){
                SweetAlert.dismis()
                Go(this).move(MainActivity::class.java, clearPrevious = true)
            }
        }

        viewModel.getError().observe(this){
            if (it != null){
                SweetAlert.dismis()
                SweetAlert.onFailure(this, it)
            }
        }
    }
}