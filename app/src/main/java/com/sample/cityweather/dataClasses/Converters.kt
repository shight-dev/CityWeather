package com.sample.cityweather.dataClasses

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun uuidToString(id: UUID): String {
        return id.toString()
    }

    @TypeConverter
    fun stringToUuid(string: String): UUID {
        return UUID.fromString(string)
    }
}