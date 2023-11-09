package com.example.muhammad_idris.Network

import com.example.muhammad_idris.Api.ApiServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkConfig {
    val BASE_URL:String = "https://databaseidris.000webhostapp.com/mahasiswa/"
    val GAMBAR_URL : String ="https://databaseidris.000webhostapp.com/mahasiswa/data_file/"
    private fun setOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(15L, TimeUnit.SECONDS)
            .build()
    }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(setOkHttp())
            .build()
    }

    fun getService(): ApiServices {
        return getRetrofitInstance().create(ApiServices::class.java)
    }
}