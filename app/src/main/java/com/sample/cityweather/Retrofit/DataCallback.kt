package com.sample.cityweather.Retrofit

interface DataCallback {
    fun setData(data: String)

    fun setId(id: String)

    fun setNoData()
}