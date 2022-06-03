package id.rafli.mystore.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.rafli.mystore.R
import id.rafli.mystore.adapter.ProdukAdapter
import id.rafli.mystore.databinding.ActivityMainBinding
import id.rafli.mystore.databinding.SheetMenuBinding
import id.rafli.mystore.helper.BaseActivity
import id.rafli.mystore.helper.SweetAlert
import id.rafli.mystore.helper.viewBinding
import id.rafli.mystore.model.Produk
import id.rafli.mystore.ui.addProduk.AddProdukActivity
import id.rafli.mystore.ui.editProduk.EditProdukActivity
import id.rafli.mystore.ui.produk.ProdukCallback
import id.rafli.mystore.ui.produk.ProdukViewModel
import id.rafli.mystore.utilities.Go

class MainActivity : BaseActivity(),ProdukCallback {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var viewModel: ProdukViewModel
    private var listProduk:ArrayList<Produk> = ArrayList()
    private lateinit var produkAdapter:ProdukAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        statusPutih()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ProdukViewModel::class.java]
        viewModel.context = this

        produkAdapter = ProdukAdapter(listProduk, this)
        binding.rvData.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = produkAdapter
        }

        action()
        observeData()

        viewModel.getAll()
    }

    override fun onProductClick(data: Produk) {
        val sheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val sheetBinding: SheetMenuBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.sheet_menu,
            null,
            false
        )
        sheetDialog.dismissWithAnimation = true

        sheetDialog.setContentView(sheetBinding.root)
        sheetBinding.tvEdit.setOnClickListener {
            sheetDialog.dismiss()
            Go(this).move(EditProdukActivity::class.java, data = data)
        }

        sheetBinding.tvDetail.setOnClickListener {
            sheetDialog.dismiss()
            Go(this).move(DetailProdukActivity::class.java, data = data)
        }

        sheetBinding.tvHapus.setOnClickListener {
            sheetDialog.dismiss()
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Produk")
            alertDialog.setMessage("Apakah anda ingin menghapus produk ${data.name} ?")
            alertDialog.setPositiveButton("Ya") { _, _ ->
                viewModel.delete(data)
            }
            alertDialog.setNegativeButton("Tidak") { _, _ ->

            }
            alertDialog.show()
        }

        sheetDialog.show()
    }

    private fun action(){

        binding.floatAdd.setOnClickListener {
            Go(this).move(AddProdukActivity::class.java)
        }

        binding.svCari.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                produkAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                produkAdapter.filter.filter(query)
                return false
            }

        })

        binding.tvReload.setOnClickListener {
            viewModel.getAll()
        }

        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = false
            viewModel.getAll()
        }
    }

    private fun observeData(){
        viewModel.getLoading().observe(this){
            if (it != null){
                if (it){
                    binding.divKosong.visibility = View.GONE
                    binding.shimmer.visibility = View.VISIBLE
                    binding.shimmer.startShimmer()
                }else{
                    binding.shimmer.visibility = View.GONE
                    binding.shimmer.stopShimmer()
                }
            }
        }

        viewModel.getError().observe(this){
            if (it != null){
                binding.divKosong.visibility = View.VISIBLE
                SweetAlert.onFailure(this, it)
            }
        }

        viewModel.getResponse().observe(this){
            if (it != null){
                if(it.produks.isNotEmpty()){
                    listProduk.clear()
                    listProduk.addAll(it.produks)
                    produkAdapter.notifyDataSetChanged()
                }else{
                    binding.divKosong.visibility = View.VISIBLE
                }

            }
        }

        viewModel.getResponseDelete().observe(this){
            if (it != null){
                listProduk.remove(it.produk)
                produkAdapter.notifyDataSetChanged()
            }
        }
    }
}