package com.example.muhammad_idris.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.muhammad_idris.Model.DataItem
import com.example.muhammad_idris.Model.Response
import com.example.muhammad_idris.Network.NetworkConfig
import com.example.muhammad_idris.TambahActivity
import com.example.muhammad_idris.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import android.app.AlertDialog
import android.widget.Toast
import com.example.muhammad_idris.UbahActivity

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener(this)

        // Panggil metode getPosts() untuk memuat data dari server
        getPosts()
        binding.btnTambah.setOnClickListener {
            // Membuat intent untuk pindah ke TambahActivity
            val intent = Intent(this, TambahActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onRefresh() {
        // Panggil metode getPosts() untuk memuat ulang data dari server
        getPosts()
    }

    private fun getPosts() {
        NetworkConfig().getService()
            .getmahasiswa()
            .enqueue(object : Callback<Response> {
                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    binding.progressIndicator.visibility = View.GONE
                    if (response.isSuccessful) {
                        val receivedDatas = response.body()?.data
                        setToAdapter(receivedDatas as List<DataItem>?)
                    }
                    binding.swipeRefreshLayout.isRefreshing = false // Beritahu bahwa proses refresh selesai
                }

                @SuppressLint("SuspiciousIndentation")
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    Log.d("Retrofit failed", "onFailure : ${t.stackTrace}")
                    binding.swipeRefreshLayout.isRefreshing = false // Beritahu bahwa proses refresh selesai
                }
            })
    }

    private fun setToAdapter(receivedDatas: List<DataItem>?) {
        binding.newsList.adapter = null
        val adapter = TekomAdapter(receivedDatas) {
            showOptionsDialog(it)


        }
        val lm = LinearLayoutManager(this)
        binding.newsList.layoutManager = lm
        binding.newsList.itemAnimator = DefaultItemAnimator()
        binding.newsList.adapter = adapter

    }
    private fun showOptionsDialog(dataItem: DataItem?) {
        val options = arrayOf("Edit Data", "Delete Data")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an option")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> editData(dataItem)
                1 -> deleteData(dataItem)
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun editData(dataItem: DataItem?) {
        val intent = Intent(this, UbahActivity::class.java)
        intent.putExtra("nama", dataItem?.namalengkap)
        intent.putExtra("nim", dataItem?.nim.toString())
        intent.putExtra("gender", dataItem?.gender)
        intent.putExtra("prodi", dataItem?.idprodi)
        startActivity(intent)
    }

    private fun deleteData(dataItem: DataItem?) {
        val apiServices = NetworkConfig().getService()
        dataItem?.nim?.let {
            apiServices.hapusMahasiswa(it).enqueue(object : Callback<Response> {
                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                        getPosts() // Refresh data setelah penghapusan
                    } else {
                        Toast.makeText(this@MainActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}

