package com.sample.cityweather.Retrofit

interface DataCallback {
    fun setData(data: String)

    fun setNoData()
}