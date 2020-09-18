package com.example.movietv.data.model

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
@TypeConverters(value = [ListStringConverter::class, ListCastConverter::class])
data class TvShowModel (
    @PrimaryKey(autoGenerate = true)
    val idInDb : Long = 0,
    val id : Long = -1,
    @SerializedName("original_language")
    val language : String = "",
    @SerializedName("name")
    val title : String = "",
    @SerializedName("poster_path")
    val posterUrl : String = "",
    @SerializedName("backdrop_path")
    val backdropUrl : String = "",
    @SerializedName("adult")
    val isForAdult : Boolean? = false,
    val overview : String = "",
    val genre : List<String> = listOf(),
    @SerializedName("runtime")
    val duration : Int = 0,
    @SerializedName("actor_list")
    val castList : List<Cast>? = listOf(),
    @SerializedName("release_date")
    val releasedDate : String? = "",
    @SerializedName("vote_average")
    val rating : Double? = 0.0,
    @SerializedName("vote_count")
    val vote : Int = 0,
    var isFav : Boolean = false
) : Parcelable