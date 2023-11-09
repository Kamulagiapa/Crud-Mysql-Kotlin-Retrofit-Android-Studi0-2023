package com.example.muhammad_idris.Api

import com.example.muhammad_idris.Model.Response
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {

    @GET("retrieve.php")
    fun getmahasiswa():
            Call<Response>
    @FormUrlEncoded
    @POST("create.php")
    fun tambahMahasiswa(
        @Field("namalengkap") namalengkap: String,
        @Field("nim") nim: String,
        @Field("gender") gender:String,
        @Field("idprodi") idprodi: String
    ): Call<Response>
    @FormUrlEncoded
    @POST("delete.php")
    fun hapusMahasiswa(@Field("nim") id: Int): Call<Response>
    @FormUrlEncoded
    @POST("update.php")
    fun perbaruiMahasiswa(
        @Field("nim") id: String,
        @Field("namalengkap") namalengkap: String,
        @Field("gender") gender: String,
        @Field("idprodi") idprodi: String
    ): Call<Response>
}