package id.rafli.mystore.ui

import android.os.Bundle
import com.bumptech.glide.Glide
import id.rafli.mystore.R
import id.rafli.mystore.app.App.Companion.helper
import id.rafli.mystore.databinding.ActivityDetailProdukBinding
import id.rafli.mystore.helper.BaseActivity
import id.rafli.mystore.helper.Config
import id.rafli.mystore.helper.Config.extra_model
import id.rafli.mystore.helper.viewBinding
import id.rafli.mystore.model.Produk

class DetailProdukActivity : BaseActivity() {
    private val binding by viewBinding(ActivityDetailProdukBinding::inflate)
    private var produk = Produk()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolbar("Detail Produk", binding.toolbar)
        produk = intent.getParcelableExtra(extra_model) ?: Produk()
        setData()
    }

    private fun setData(){
        binding.tvHarga.text = helper.convertRupiah(produk.price)
        binding.tvStok.text = "Stok : ${produk.stok}"
        binding.tvNama.text = produk.name
        binding.tvDeskripsi.text = produk.deskripsi
        binding.tvKode.text = produk.code

        if (!produk.image.contains("default")){
            Glide.with(this).load(Config.BASE_STORAGE + produk.image).placeholder(R.drawable.ic_product).error(R.drawable.ic_product).into(binding.ivProduk)
        }
    }
}