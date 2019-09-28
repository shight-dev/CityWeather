package com.sample.cityweather.Retrofit.PictureController

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val PICTURE_API_KEY = "13775761-ac08c83920df608f2b2f1b872"


interface PictureApi {
    @GET("api/")
    fun loadPictureList(@Query("q")cityName:String, @Query("key")appId:String = PICTURE_API_KEY
    ,@Query("category") category:String = "place") : Call<PictureResponse>
}