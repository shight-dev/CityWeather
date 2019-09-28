package com.sample.cityweather.mvpViews

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.cityweather.DataClasses.WeatherData

interface WeatherListView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun updateUi(weatherList :List<WeatherData>)

    @StateStrategyType(SkipStrategy::class)
    fun updateWeather(position:Int)
}