package com.example.movietv.data.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieModel (
    @PrimaryKey(autoGenerate = false)
    val id : Long,
    @SerializedName("original_language")
    val language : String = "",
    val title : String = "",
    @SerializedName("poster_path")
    val posterUrl : String = "",
    @SerializedName("backdrop_path")
    val backdropUrl : String? = "",
    @SerializedName(    "adult")
    val isForAdult : Boolean? = false,
    val overview : String = "",

    val genres : List<Genre>? = listOf(),

    @SerializedName("runtime")
    val duration : Int = 0,

    var castList : List<Cast>? = listOf(),

    @SerializedName("release_date")
    val releasedDate : String? = "",
    @SerializedName("vote_average")
    val rating : Double? = 0.0,
    @SerializedName("vote_count")
    val vote : Int = 0,
    var isFav : Boolean = false,
    var availableInOfflineMode : Boolean = false
)