package com.sample.cityweather.daggerWork

import android.app.Application

class App : Application() {

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .dataWorkerModule(DataWorkerModule())
            .retrofitWeatherControllerModule(RetrofitWeatherControllerModule())
            .retrofitPictureControllerModule(RetrofitPictureControllerModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component = buildComponent()
    }

    companion object {
        lateinit var component: AppComponent
    }
}