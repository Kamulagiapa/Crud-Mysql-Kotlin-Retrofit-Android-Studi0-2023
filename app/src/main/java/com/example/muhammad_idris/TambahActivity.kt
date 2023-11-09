package com.example.muhammad_idris

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.muhammad_idris.Api.ApiServices
import com.example.muhammad_idris.Model.Response
import com.example.muhammad_idris.Network.NetworkConfig
import com.example.muhammad_idris.R
import retrofit2.Call
import retrofit2.Callback

class TambahActivity : AppCompatActivity() {
    private lateinit var namaEditText: EditText
    private lateinit var nimEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var idprodiEditText: EditText
    private lateinit var simpanButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah)

        namaEditText = findViewById(R.id.nama)
        nimEditText = findViewById(R.id.nim)
        idprodiEditText =findViewById(R.id.idprodi)
        genderEditText = findViewById(R.id.gender)
        simpanButton = findViewById(R.id.btn_simpan)

        simpanButton.setOnClickListener {
            tambahData()
        }
    }

    private fun tambahData() {
        val namalengkap = namaEditText.text.toString()
        val nim = nimEditText.text.toString()
        val gender = genderEditText.text.toString()
        val idprodi = idprodiEditText.text.toString()

        val apiServices = NetworkConfig().getService()
        apiServices.tambahMahasiswa(namalengkap, nim, gender,idprodi).enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@TambahActivity, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@TambahActivity, "Gagal menambahkan data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Toast.makeText(this@TambahActivity, "Gagal menambahkan data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
