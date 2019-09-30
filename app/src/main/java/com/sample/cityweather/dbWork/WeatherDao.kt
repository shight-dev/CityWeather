package com.sample.cityweather.dbWork

import androidx.room.*
import com.sample.cityweather.dataClasses.WeatherData

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