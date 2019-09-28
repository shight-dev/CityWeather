package com.sample.cityweather.DaggerWork

import android.app.Application

class App : Application() {

    protected fun buildComponent(): AppComponent {
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