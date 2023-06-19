package com.lucas.soccerchallenge.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    fun isSameMonthYear(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
    }

    fun getMonthYear(date: Date): String {
        val df = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        return df.format(date)
    }

    fun getUIFormattedDate(date: Date): String {
        val df = SimpleDateFormat("MMM dd, yyyy 'at' kk:mm", Locale.getDefault())
        return df.format(date)
    }

    fun getMonthDayNumber(date: Date): String {
        val df = SimpleDateFormat("dd", Locale.getDefault())
        return df.format(date)
    }

    fun getWeekDayNameShort(date: Date): String {
        val df = SimpleDateFormat("EEE", Locale.getDefault())
        return df.format(date)
    }

}
