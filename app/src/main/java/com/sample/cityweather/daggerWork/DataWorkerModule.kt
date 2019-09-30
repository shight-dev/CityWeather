package com.sample.cityweather.daggerWork

import android.content.Context
import com.sample.cityweather.dbWork.DataWorker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DataWorkerModule {

    @Provides
    @Singleton
    fun provideDataWorker(context: Context): DataWorker {
        return DataWorker(context)
    }
}