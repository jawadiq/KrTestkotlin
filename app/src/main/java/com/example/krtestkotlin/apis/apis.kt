package com.example.krtestkotlin.apis

import com.example.krtestkotlin.models.Main
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface apis {
    @GET ("forecast/{api}/{lat},{lon}")
    fun getModels(@Path("api") key:String,@Path("lat") lat:Double, @Path("lon") lon:Double): Call<Main>
}