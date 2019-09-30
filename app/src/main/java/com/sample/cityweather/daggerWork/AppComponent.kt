package com.sample.cityweather.daggerWork

import com.sample.cityweather.presenters.WeatherEditPresenter
import com.sample.cityweather.presenters.WeatherListPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataWorkerModule::class, ContextModule::class, RetrofitWeatherControllerModule::class, RetrofitPictureControllerModule::class])
@Singleton
interface AppComponent {

    fun inject(weatherListPresenter: WeatherListPresenter)
    fun inject(weatherEditPresenter: WeatherEditPresenter)
}