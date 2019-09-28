package com.sample.cityweather.DaggerWork

import com.sample.cityweather.Fragments.EditFragment
import com.sample.cityweather.Fragments.WeatherListFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataWorkerModule::class, ContextModule::class, RetrofitWeatherControllerModule::class, RetrofitPictureControllerModule::class])
@Singleton
interface AppComponent {

    fun inject(weatherListFragment : WeatherListFragment)
    fun inject(editFragment : EditFragment)
}