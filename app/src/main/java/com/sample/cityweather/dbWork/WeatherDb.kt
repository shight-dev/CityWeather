package com.sample.cityweather.dbWork

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.cityweather.dataClasses.WeatherData

@Database(entities = [WeatherData::class], version = 1)
abstract class WeatherDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}