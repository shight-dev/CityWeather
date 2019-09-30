package com.sample.cityweather.daggerWork

import com.sample.cityweather.retrofit.pictureController.PictureController
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