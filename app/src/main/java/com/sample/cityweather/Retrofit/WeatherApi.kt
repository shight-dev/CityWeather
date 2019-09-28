package com.sample.cityweather.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val WEATHER_API_KEY = "84645f00e4d59563e6157c7f87d1831e"

interface WeatherApi {

    @GET("data/2.5/weather")
    fun loadWeather(@Query("q") cityName: String, @Query("APPID") appId: String = WEATHER_API_KEY): Call<WeatherResponse>
}