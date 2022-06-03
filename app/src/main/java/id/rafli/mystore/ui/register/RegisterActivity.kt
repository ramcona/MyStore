package id.rafli.mystore.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import id.rafli.mystore.R
import id.rafli.mystore.databinding.ActivityLoginBinding
import id.rafli.mystore.databinding.ActivityRegisterBinding
import id.rafli.mystore.helper.BaseActivity
import id.rafli.mystore.helper.SweetAlert
import id.rafli.mystore.helper.viewBinding
import id.rafli.mystore.model.User
import id.rafli.mystore.ui.MainActivity
import id.rafli.mystore.ui.login.AuthViewModel
import id.rafli.mystore.utilities.Go

class RegisterActivity : BaseActivity() {
    private lateinit var viewModel: AuthViewModel
    private val binding by viewBinding(ActivityRegisterBinding::inflate)

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
        binding.btnDaftar.setOnClickListener {
            val fullname = binding.edtFullname.text.toString()
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            val passwordKonf = binding.edtPasswordKonf.text.toString()
            when {
                fullname.isEmpty() -> {
                    toast.show("Nama Lengkap tidak boleh kosong", this)
                }
                username.isEmpty() -> {
                    toast.show("Username tidak boleh kosong", this)
                }
                password.isEmpty() -> {
                    toast.show("Password tidak boleh kosong", this)
                }
                (password != passwordKonf) ->{
                    toast.show("Konfirmasi password tidak sesui", this)
                }
                else -> {
                    val user = User()
                    user.fullname = fullname
                    user.username = username
                    user.password = password
                    viewModel.register(user)
                }
            }
        }

        binding.tvMasuk.setOnClickListener {
            finish()
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