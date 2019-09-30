package com.sample.cityweather.retrofit.weatherController

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val WEATHER_API_KEY = "84645f00e4d59563e6157c7f87d1831e"

private const val KEY = "APPID"
private const val CITY_KEY = "q"

interface WeatherApi {

    @GET("data/2.5/weather")
    fun loadWeather(@Query(CITY_KEY) cityName: String, @Query(KEY) appId: String = WEATHER_API_KEY): Call<WeatherResponse>
}