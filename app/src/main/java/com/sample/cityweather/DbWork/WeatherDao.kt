package com.sample.cityweather.DbWork

import androidx.room.*
import com.sample.cityweather.DataClasses.WeatherData

@Dao
interface WeatherDao {

    @Insert
    fun addWeather(weather: WeatherData)

    @Delete
    fun removeWeather(weather: WeatherData)

    @Update
    fun updateWeather(weather: WeatherData)

    @Query("select * from WeatherData")
    fun getData(): List<WeatherData>

    @Query("select * from WeatherData where id =:id")
    fun getData(id: String): WeatherData
}