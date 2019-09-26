package com.sample.cityweather.DaggerWork

import com.sample.cityweather.Activities.MainActivity
import com.sample.cityweather.Fragments.WeatherListFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataWorkerModule::class, ContextModule::class])
@Singleton
interface AppComponent {

    fun inject(weatherListFragment : WeatherListFragment)
}