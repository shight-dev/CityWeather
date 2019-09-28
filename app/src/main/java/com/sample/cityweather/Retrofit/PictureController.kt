package com.sample.cityweather.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.random.Random

class PictureController {
    private val BASE_URL = "https://pixabay.com/"

    fun start(dataCallback: DataCallback, cityName:String) {
        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val pictureApi = retrofit.create(PictureApi::class.java)

        val call: Call<PictureResponse> = pictureApi.loadPictureList(cityName)

        call.enqueue(object : Callback<PictureResponse> {
            override fun onFailure(call: Call<PictureResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<PictureResponse>,
                response: Response<PictureResponse>
            ) {
                val pictureDataList = response.body()?.hits
                pictureDataList?.let {
                    val size = pictureDataList.size
                    if(size > 0){
                        dataCallback.setData(pictureDataList[Random(Date().time).nextInt(size)].largeImageURL)
                    }
                }
            }

        })
    }
}