package com.sample.cityweather.DataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity
@TypeConverters(Converters::class)
data class WeatherData(
    var weather: String = "",
    var city: String = "",
    var locale: String = "",
    var photoId: String = ""
) {
    @PrimaryKey
    var id: UUID = UUID.randomUUID()
}