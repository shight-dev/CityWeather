package com.sample.cityweather.Retrofit.PictureController

import com.google.gson.GsonBuilder
import com.sample.cityweather.Retrofit.DataCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.random.Random

class PictureController {
    private val BASE_URL = "https://pixabay.com/"

    private var pictureApi: PictureApi

    init {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        pictureApi = retrofit.create(PictureApi::class.java)
    }

    fun start(dataCallback: DataCallback, cityName: String) {
        val call: Call<PictureResponse> = pictureApi.loadPictureList(cityName)
        call.enqueue(object : Callback<PictureResponse> {
            override fun onFailure(call: Call<PictureResponse>, t: Throwable) {
                dataCallback.setNoData()
            }

            override fun onResponse(
                call: Call<PictureResponse>,
                response: Response<PictureResponse>
            ) {
                val pictureDataList = response.body()?.hits
                pictureDataList?.let {
                    val size = pictureDataList.size
                    if (size > 0) {
                        val pictureData = pictureDataList[Random(Date().time).nextInt(size)]
                        val url = pictureData.largeImageURL
                        val id = pictureData.id
                        url?.let {
                            dataCallback.setData(url)
                        }
                        id?.let{
                            dataCallback.setId(id)
                        }
                    }
                }
            }

        })
    }

    fun loadById(dataCallback: DataCallback, id:String){
        val call: Call<PictureResponse> = pictureApi.loadPictureById(id)
        call.enqueue(object : Callback<PictureResponse> {
            override fun onFailure(call: Call<PictureResponse>, t: Throwable) {
                dataCallback.setNoData()
            }
            override fun onResponse(
                call: Call<PictureResponse>,
                response: Response<PictureResponse>
            ) {
                val pictureDataList = response.body()?.hits
                pictureDataList?.let {
                    val size = pictureDataList.size
                    if (size > 0) {
                        val pictureData = pictureDataList[0]
                        val url = pictureData.largeImageURL
                        url?.let {
                            dataCallback.setData(url)
                        }
                    }
                }
            }

        })
    }
}