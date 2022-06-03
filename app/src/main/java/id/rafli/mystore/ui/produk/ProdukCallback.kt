package id.rafli.mystore.ui.produk

import id.rafli.mystore.model.Produk

interface ProdukCallback {
    fun onProductClick(data: Produk)
}