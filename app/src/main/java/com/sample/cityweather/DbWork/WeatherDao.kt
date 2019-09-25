package com.sample.cityweather.DbWork

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sample.cityweather.DataClasses.WeatherData

@Dao
interface WeatherDao {

    @Insert
    fun addWeather(weather:WeatherData)

    @Delete
    fun removeWeather(weather:WeatherData)

    @Query("select * from WeatherData")
    fun getData(): List<WeatherData>
}