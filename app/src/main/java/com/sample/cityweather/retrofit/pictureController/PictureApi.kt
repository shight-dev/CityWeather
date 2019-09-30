package com.sample.cityweather.retrofit.pictureController

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val PICTURE_API_KEY = "13775761-ac08c83920df608f2b2f1b872"

private const val KEY = "key"
private const val CATEGORY = "category"
private const val ID = "id"
private const val PLACE = "place"
private const val CITY_KEY = "q"

interface PictureApi {
    @GET("api/")
    fun loadPictureList(
        @Query(CITY_KEY) cityName: String, @Query(KEY) appId: String = PICTURE_API_KEY
        , @Query(CATEGORY) category: String = PLACE
    ): Call<PictureResponse>

    @GET("api/")
    fun loadPictureById(
        @Query(ID) id: String,
        @Query(KEY) appId: String = PICTURE_API_KEY
    ): Call<PictureResponse>
}