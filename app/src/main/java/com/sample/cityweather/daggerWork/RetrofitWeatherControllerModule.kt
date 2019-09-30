package com.sample.cityweather.daggerWork

import com.sample.cityweather.retrofit.weatherController.WeatherController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RetrofitWeatherControllerModule {
    @Provides
    @Singleton
    fun getRetrofitController(): WeatherController {
        return WeatherController()
    }

}