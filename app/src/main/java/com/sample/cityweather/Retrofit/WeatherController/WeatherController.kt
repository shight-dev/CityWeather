package com.sample.cityweather.Retrofit.WeatherController

import com.google.gson.GsonBuilder
import com.sample.cityweather.DataClasses.WeatherData
import com.sample.cityweather.Retrofit.DataCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class WeatherController {

    private val BASE_URL = "http://api.openweathermap.org/"

    private val weatherApi :WeatherApi

    init {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun start(dataCallback: DataCallback, weatherData: WeatherData) {
        val call: Call<WeatherResponse> =
            weatherApi.loadWeather("${weatherData.city},${weatherData.locale}")
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                //TODO toast error
            }

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                val temp = response.body()?.main?.temp
                temp?.let {
                    dataCallback.setData(temp)
                }
                    ?: dataCallback.setNoData()
            }

        })
    }

}