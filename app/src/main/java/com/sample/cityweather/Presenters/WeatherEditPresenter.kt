package com.sample.cityweather.Presenters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.sample.cityweather.DaggerWork.App
import com.sample.cityweather.DataClasses.WeatherData
import com.sample.cityweather.DataWorkers.WeatherConverter
import com.sample.cityweather.DbWork.DataWorker
import com.sample.cityweather.Retrofit.DataCallback
import com.sample.cityweather.Retrofit.PictureController.PictureController
import com.sample.cityweather.Retrofit.WeatherController.WeatherController
import com.sample.cityweather.mvpViews.WeatherEditView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception
import javax.inject.Inject

@InjectViewState
class WeatherEditPresenter : MvpPresenter<WeatherEditView>() {
    @Inject
    lateinit var dataWorker: DataWorker

    @Inject
    lateinit var weatherController: WeatherController

    @Inject
    lateinit var pictureController: PictureController

    private var weatherData: WeatherData? = null

    private var isNew: Boolean = false

    init {
        App.component.inject(this)
    }

    fun onCreate(id: String?) {
        id?.let {
            weatherData = dataWorker.getWeather(id)
        }
        weatherData ?: let {
            weatherData = WeatherData()
            isNew = true
        }
    }

    fun onSaveBtnClick() {
        weatherData?.let {
            if (isNew) {
                dataWorker.addWeather(weatherData!!)
            } else {
                dataWorker.updateWeather(weatherData!!)
            }
        }
    }

    fun onRefreshDataBtnClick() {
        weatherData?.let {
            weatherController.start(object : DataCallback {
                override fun setNoData() {
                    viewState.updateTemp("Incorrect city")
                    weatherData!!.weather = ""
                }

                override fun setData(data: String) {
                    val res = WeatherConverter.convertKelvin(data)
                    weatherData!!.weather = res
                    viewState.updateTemp(res)
                }
            }, weatherData!!)
            val cityName = weatherData?.city ?: ""
            if (!cityName.contentEquals("")) {
                pictureController.start(object : DataCallback {
                    override fun setNoData() {
                    }

                    override fun setData(data: String) {
                            weatherData?.photoId = data
                            Picasso.get().load(weatherData?.photoId).into(object : Target {
                                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                                    //do nothing
                                    //TODO show something
                                }

                                override fun onBitmapFailed(
                                    e: Exception?,
                                    errorDrawable: Drawable?
                                ) {
                                    //do nothing
                                    //TODO show something
                                }

                                override fun onBitmapLoaded(
                                    bitmap: Bitmap?,
                                    from: Picasso.LoadedFrom?
                                ) {
                                    bitmap?.let {
                                        viewState.updateImage(bitmap)
                                    }
                                }

                            })
                    }

                }, cityName)
            }
        }
    }

    fun onStart() {
        if (!isNew) {
            weatherData?.let {
                if (weatherData?.photoId?.contentEquals("") != true) {
                    Picasso.get().load(weatherData?.photoId).into(object : Target {
                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            //do nothing
                            //TODO show something
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            //do nothing
                            val a =1
                            //TODO show something
                        }

                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            bitmap?.let {
                                viewState.updateImage(bitmap)
                            }
                        }

                    })
                }
            }
        }
        viewState.updateUi(
            weatherData?.city ?: "",
            weatherData?.locale ?: "",
            weatherData?.weather ?: ""
        )
    }

    fun onLocaleChange(locale: String) {
        weatherData?.locale = locale
    }

    fun onCityChange(cityName: String) {
        weatherData?.city = cityName
    }

}