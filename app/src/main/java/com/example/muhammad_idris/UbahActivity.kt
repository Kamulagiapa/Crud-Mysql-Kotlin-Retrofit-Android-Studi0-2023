package com.example.muhammad_idris

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.muhammad_idris.Model.Response
import com.example.muhammad_idris.Network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback

class UbahActivity : AppCompatActivity() {
    private lateinit var namaEditText: EditText
    private lateinit var nimEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var prodiEditText: EditText
    private lateinit var simpanButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah)

        namaEditText = findViewById(R.id.nama)
        nimEditText = findViewById(R.id.nim)
        genderEditText = findViewById(R.id.gender)
        prodiEditText = findViewById(R.id.idprodi)
        simpanButton = findViewById(R.id.btn_simpan)

        // Ambil data yang akan diubah dari Intent
        val intent = intent
        val nama = intent.getStringExtra("nama")
        val nim = intent.getStringExtra("nim")
        val gender = intent.getStringExtra("gender")
        val prodi = intent.getStringExtra("prodi")

        // Isi EditText dengan data yang diambil
        namaEditText.setText(nama)
        nimEditText.setText(nim)
        genderEditText.setText(gender)
        prodiEditText.setText(prodi)

        // Implementasikan logika untuk menyimpan perubahan data
        simpanButton.setOnClickListener {
            val updatedNama = namaEditText.text.toString()
            val updatedNim = nimEditText.text.toString()
            val updatedGender = genderEditText.text.toString()
            val updatedProdi = prodiEditText.text.toString()

            val apiServices = NetworkConfig().getService()
            apiServices.perbaruiMahasiswa(updatedNim, updatedNama, updatedGender, updatedProdi)
                .enqueue(object : Callback<Response> {
                    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@UbahActivity, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK)
                            finish()
                        } else {
                            // Gagal memperbarui data
                            Toast.makeText(this@UbahActivity, "Gagal mengubah data", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        // Gagal melakukan permintaan ke server
                        Toast.makeText(this@UbahActivity, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
