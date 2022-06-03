package id.rafli.mystore.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.rafli.mystore.R
import id.rafli.mystore.adapter.holder.ProdukHolder
import id.rafli.mystore.model.Produk
import id.rafli.mystore.ui.produk.ProdukCallback

class ProdukAdapter(val list:ArrayList<Produk>, val callback: ProdukCallback): RecyclerView.Adapter<ProdukHolder>(),
    Filterable {
    var data: ArrayList<Produk> =  list
    var listFiltered: ArrayList<Produk> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdukHolder {
        return  ProdukHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_produk, parent, false))
    }

    override fun onBindViewHolder(holder: ProdukHolder, position: Int) {
        holder.setData(listFiltered[position], callback)
    }

    override fun getItemCount(): Int {
        return listFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val result2 = java.util.ArrayList<Produk>()
                listFiltered = data
                if (constraint != null) {
                    if ((listFiltered.size > 0)) {
                        for (map in listFiltered) {
                            if (map.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                                result2.add(map)
                            }
                        }

                    }
                    results.values = result2
                }

                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                listFiltered = results.values as java.util.ArrayList<Produk>
                notifyDataSetChanged()
            }
        }
    }
}