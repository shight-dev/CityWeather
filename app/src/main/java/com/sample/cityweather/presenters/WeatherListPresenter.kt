package com.sample.cityweather.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.sample.cityweather.daggerWork.App
import com.sample.cityweather.dataClasses.WeatherData
import com.sample.cityweather.dataWorkers.WeatherConverter
import com.sample.cityweather.dbWork.CreateCallback
import com.sample.cityweather.dbWork.DataWorker
import com.sample.cityweather.mvpViews.WeatherListView
import com.sample.cityweather.retrofit.DataCallback
import com.sample.cityweather.retrofit.weatherController.WeatherController
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

    fun onStart() {
        dataWorker.createCallback = object : CreateCallback {
            override fun created() {
                viewState.updateUi(dataWorker.getAllWeather())
            }

        }
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
            override fun setId(id: String) {
                //do nothing
            }

            override fun setNoData() {
                if (!weatherData.weather.contentEquals("")) {
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