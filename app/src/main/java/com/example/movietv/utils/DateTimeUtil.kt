package com.example.movietv.utils

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

object DateTimeUtil {
    fun convertMinutesToHourMinutes(minute: Int) : String {
        if(minute % 60 == 0){
            return "${minute / 60}h 0m"
        }else{
            val hour = minute / 60
            val remainingMinutes = minute - (hour * 60)
            return "${hour}h ${remainingMinutes}m"
        }
    }

    fun getYear(formatter: SimpleDateFormat, date: String) : Int{
        val calendar: Calendar = Calendar.getInstance()
        formatter.parse(date)?.let {
            calendar.time = it
        }
        return calendar.get(Calendar.YEAR)
    }

    fun getCurrentDiffWith(formatter: SimpleDateFormat, date : String) : Long {
        formatter.parse(date)?.let {
            val currentDate = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            return currentDate.dayDiffWith(it)
        }
        return 0
    }

    fun Date.dayDiffWith(date: Date) : Long = TimeUnit.MILLISECONDS.toDays(date.time - this.time)
}