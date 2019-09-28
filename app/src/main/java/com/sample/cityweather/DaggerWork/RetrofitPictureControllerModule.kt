package com.sample.cityweather.DaggerWork

import com.sample.cityweather.Retrofit.PictureController.PictureController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RetrofitPictureControllerModule {
    @Provides
    @Singleton
    fun getRetrofitController(): PictureController {
        return PictureController()
    }
}