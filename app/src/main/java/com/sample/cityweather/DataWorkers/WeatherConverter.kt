package com.sample.cityweather.DataWorkers

const val KELVIN_DIF = 273.15

object WeatherConverter {
    fun convertKelvin(kelvinString : String?):String{
        var doubleVal = kelvinString?.toDoubleOrNull()
        doubleVal?.let {
            doubleVal = doubleVal!! - KELVIN_DIF
            return if(doubleVal!!>0){
                "+"+"%.1f".format(doubleVal)
            }
            else{
                "%.1f".format(doubleVal)
            }
        }
        return ""
    }
}