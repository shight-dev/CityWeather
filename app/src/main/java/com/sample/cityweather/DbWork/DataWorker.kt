package com.sample.cityweather.DbWork

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sample.cityweather.DataClasses.WeatherData
import java.util.concurrent.Executors


class DataWorker(context: Context) {

    private val callback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Executors.newSingleThreadScheduledExecutor()
                .execute {
                    mWeatherDb.weatherDao().addWeather(WeatherData("+15", "Moscow"))
                    mWeatherDb.weatherDao().addWeather(WeatherData("+35", "Bangkok"))
                }
        }

    }

    private var mWeatherDb: WeatherDb =
        Room.databaseBuilder(context, WeatherDb::class.java, "weatherDb")
            .allowMainThreadQueries()
            .addCallback(callback)
            .build()

    fun updateWeather(weatherData: WeatherData) {
        mWeatherDb.weatherDao().updateWeather(weatherData)
    }

    fun removeWeather(weather: WeatherData) {
        mWeatherDb.weatherDao().removeWeather(weather)
    }

    fun addWeather(weather: WeatherData) {
        mWeatherDb.weatherDao().addWeather(weather)
    }

    fun getAllWeather() =
        mWeatherDb.weatherDao().getData()

    fun getWeather(cityName :String) =
        mWeatherDb.weatherDao().getData(cityName)
}