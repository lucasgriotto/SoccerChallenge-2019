package com.lucas.soccerchallenge.core.database.util

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun dateToLong(date: Date?) = date?.time


    @TypeConverter
    fun longToDate(value: Long) = Date(value)

}