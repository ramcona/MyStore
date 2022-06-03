package id.rafli.mystore.ui.addProduk

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import id.rafli.mystore.R
import id.rafli.mystore.app.App.Companion.helper
import id.rafli.mystore.databinding.ActivityAddProdukBinding
import id.rafli.mystore.helper.BaseActivity
import id.rafli.mystore.helper.SweetAlert
import id.rafli.mystore.helper.viewBinding
import id.rafli.mystore.mediapicker.Image.ImagePicker
import id.rafli.mystore.model.Produk
import id.rafli.mystore.ui.produk.ProdukViewModel
import id.rafli.mystore.utilities.Picker
import java.io.File

class AddProdukActivity : BaseActivity() {
    private val binding by viewBinding(ActivityAddProdukBinding::inflate)
    private lateinit var picker: Picker
    private var fileImage: File? = null
    private lateinit var viewModel:ProdukViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolbar(getString(R.string.teks_add_product), binding.toolbar)

        helper.setCurrencyForEditText(binding.edtHarga)
        helper.setCurrencyForEditText(binding.edtStok)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ProdukViewModel::class.java]
        viewModel.context = this

        picker = Picker(this)

        action()
        observeData()
    }

    private fun observeData(){
        viewModel.getLoading().observe(this){
            if (it != null){
                if (it){
                    SweetAlert.onLoading(this)
                }else{
                    SweetAlert.dismis()
                }
            }
        }

        viewModel.getError().observe(this){
            if (it != null){
                SweetAlert.dismis()
                SweetAlert.onFailure(this, it)
            }
        }

        viewModel.getResponse().observe(this){
            if (it != null){
                SweetAlert.dismis()
                SweetAlert.success(this, getString(R.string.produk), getString(R.string.teks_produk_berhasil_ditambah), finish = true)
            }
        }
    }

    private fun action(){
        binding.ivProduk.setOnClickListener {
            picker.ambilGambarSemua()
        }

        binding.tvHapusImage.setOnClickListener {
            binding.tvHapusImage.visibility = View.GONE
            fileImage = null
            Glide.with(this)
                .load(R.drawable.ic_product)
                .placeholder(R.drawable.ic_product)
                .error(R.drawable.ic_product)
                .into(binding.ivProduk)
        }

        binding.btnSimpan.setOnClickListener {
            val nama = binding.edtNama.text.toString()
            val stok = helper.clearRupiahString(binding.edtStok.text.toString())
            val harga = helper.clearRupiahString(binding.edtHarga.text.toString())
            val deskripsi = binding.edtDeskripsi.text.toString()
            val kodeProduk = binding.edtKode.text.toString()

            if (nama.isEmpty()){
                toast.show("Lengkapi data produk anda", this)
            }else if (kodeProduk.isEmpty()){
                toast.show("Lengkapi data produk anda", this)
            }else if (harga.isEmpty()){
                toast.show("Lengkapi data produk anda", this)
            }else if (stok.isEmpty()){
                toast.show("Lengkapi data produk anda", this)
            }else if (deskripsi.isEmpty()){
                toast.show("Lengkapi data produk anda", this)
            }else{
                val produk = Produk()
                produk.stok = stok
                produk.code = kodeProduk
                produk.name = nama
                produk.deskripsi = deskripsi
                produk.price = harga

                if (fileImage != null){
                    viewModel.addProdukWithImage(produk, fileImage!!)
                }else{
                    viewModel.addProduk(produk)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            val mpath: java.util.ArrayList<String> = data!!.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH)!!
            val file = File(mpath[0])
            fileImage = file
            Glide.with(this)
                .load(fileImage)
                .placeholder(R.drawable.ic_product)
                .error(R.drawable.ic_product)
                .into(binding.ivProduk)
            binding.tvHapusImage.visibility = View.VISIBLE
        }
    }
}