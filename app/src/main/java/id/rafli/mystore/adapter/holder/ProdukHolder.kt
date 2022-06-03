package id.rafli.mystore.adapter.holder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.rafli.mystore.R
import id.rafli.mystore.app.App.Companion.helper
import id.rafli.mystore.databinding.ItemProdukBinding
import id.rafli.mystore.helper.Config
import id.rafli.mystore.model.Produk
import id.rafli.mystore.ui.produk.ProdukCallback

class ProdukHolder(var item:ItemProdukBinding): RecyclerView.ViewHolder(item.root) {

    val context: Context = itemView.context

    fun setData(data:Produk,callback:ProdukCallback){
        if (!data.image.isEmpty()){
            Glide.with(context).load(Config.BASE_STORAGE + data.image).placeholder(R.drawable.ic_product).error(R.drawable.ic_product).into(item.ivProduk)
        }
        item.tvNama.text = data.name
        item.tvStok.text = "Stok : ${data.stok}"
        item.tvHarga.text = helper.convertRupiah(data.price)

        item.tvHarga.rootView.setOnClickListener {
            callback.onProductClick(data)
        }
    }
}