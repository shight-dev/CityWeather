package com.sample.cityweather.mvpViews

import android.graphics.Bitmap
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface WeatherEditView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun updateImage(bitmap: Bitmap)

    @StateStrategyType(SkipStrategy::class)
    fun updateTemp(temp: String)

    @StateStrategyType(SkipStrategy::class)
    fun updateUi(cityName: String, locale: String, weather: String)
}