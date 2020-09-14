package com.example.movietv.utils

object DateTimeConverter {
    fun convertMinutesToHourMinutes(minute : Int) : String {
        if(minute % 60 == 0){
            return "${minute / 60}h 0m"
        }else{
            val hour = minute / 60
            val remainingMinutes = minute - (hour * 60)
            return "${hour}h ${remainingMinutes}m"
        }
    }
}