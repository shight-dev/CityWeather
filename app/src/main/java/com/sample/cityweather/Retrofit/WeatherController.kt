package com.sample.cityweather.Retrofit

import com.google.gson.GsonBuilder
import com.sample.cityweather.DataClasses.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class WeatherController {

    private val BASE_URL = "http://api.openweathermap.org/"

    fun start(dataCallback: DataCallback, weatherData:WeatherData) {
        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val weatherApi = retrofit.create(WeatherApi::class.java)

        val call: Call<WeatherResponse> = weatherApi.loadWeather("${weatherData.city},${weatherData.locale}")

        call.enqueue(object : Callback<WeatherResponse>{
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                val a =1
            }

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                dataCallback.setData(response.body()?.main?.temp)
            }

        })
    }

}