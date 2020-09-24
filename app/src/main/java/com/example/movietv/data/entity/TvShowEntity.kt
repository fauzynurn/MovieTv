package com.example.movietv.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.movietv.utils.converter.ListCastConverter
import com.example.movietv.utils.converter.ListStringConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class TvShowEntity (
    @PrimaryKey(autoGenerate = false)
    val id : Long,
    @SerializedName("name")
    val title : String = "",
    @SerializedName("poster_path")
    val posterUrl : String = "",
    @SerializedName("vote_average")
    val rating : Double? = 0.0,
    @SerializedName("vote_count")
    val vote : Int = 0
) : Parcelable