package com.sample.cityweather.retrofit

interface DataCallback {
    fun setData(data: String)

    fun setId(id: String)

    fun setNoData()
}