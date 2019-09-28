package com.sample.cityweather.DaggerWork

import com.sample.cityweather.Retrofit.WeatherController.WeatherController
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