package com.example.movietv.utils.converter

import androidx.room.TypeConverter
import com.example.movietv.data.model.Cast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class ListCastConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToList(data: String): List<Cast> {
        val listType: Type = object : TypeToken<List<Cast>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Cast>): String {
        return gson.toJson(someObjects)
    }
}