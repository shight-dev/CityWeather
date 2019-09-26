package com.sample.cityweather.DaggerWork

import android.content.Context
import com.sample.cityweather.DbWork.DataWorker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DataWorkerModule {

    @Provides
    @Singleton
    fun provideDataWorker(context: Context):DataWorker{
        return DataWorker(context)
    }
}