package com.sample.cityweather.Presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.sample.cityweather.DaggerWork.App
import com.sample.cityweather.DataClasses.WeatherData
import com.sample.cityweather.DataWorkers.WeatherConverter
import com.sample.cityweather.DbWork.DataWorker
import com.sample.cityweather.mvpViews.WeatherListView
import com.sample.cityweather.Retrofit.DataCallback
import com.sample.cityweather.Retrofit.WeatherController.WeatherController
import javax.inject.Inject

@InjectViewState
class WeatherListPresenter : MvpPresenter<WeatherListView>() {

    @Inject
    lateinit var dataWorker: DataWorker

    @Inject
    lateinit var weatherController: WeatherController

    init {
        App.component.inject(this)
    }

    fun onUpdateBtnClick() {
        viewState.updateUi(dataWorker.getAllWeather())
    }

    fun onResume() {
        viewState.updateUi(dataWorker.getAllWeather())
    }

    fun onDeleteBtnClick(weatherData: WeatherData) {
        dataWorker.removeWeather(weatherData)
        viewState.updateUi(dataWorker.getAllWeather())
    }

    fun onItemUpdate(weatherData: WeatherData, position: Int) {
        weatherController.start(object : DataCallback {
            override fun setNoData() {
                if(!weatherData.weather.contentEquals("")) {
                    weatherData.weather = ""
                    dataWorker.updateWeather(weatherData)
                    viewState.updateWeather(position)
                }
            }

            override fun setData(data: String) {
                val weatherString = WeatherConverter.convertKelvin(data)
                if (!weatherString.contentEquals(weatherData.weather)) {
                    weatherData.weather = weatherString
                    dataWorker.updateWeather(weatherData)
                    viewState.updateWeather(position)
                }
            }

        }, weatherData)
    }
}