package com.sample.cityweather.DaggerWork

import com.sample.cityweather.Presenters.WeatherEditPresenter
import com.sample.cityweather.Presenters.WeatherListPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataWorkerModule::class, ContextModule::class, RetrofitWeatherControllerModule::class, RetrofitPictureControllerModule::class])
@Singleton
interface AppComponent {

    fun inject(weatherListPresenter: WeatherListPresenter)
    fun inject(weatherEditPresenter: WeatherEditPresenter)
}