package com.example.movietv.utils.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class ListStringConverter {

    @TypeConverter
    fun stringToList(data: String): List<String> {
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<String>): String {
        return Gson().toJson(someObjects)
    }
}