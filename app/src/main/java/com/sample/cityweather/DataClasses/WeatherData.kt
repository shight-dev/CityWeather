package com.sample.cityweather.DataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO change primary key
@Entity
data class WeatherData(var weather:String="", @PrimaryKey var city:String="", var photoId:String="")