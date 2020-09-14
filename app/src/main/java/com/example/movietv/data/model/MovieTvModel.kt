package com.example.movietv.data.model

import com.google.gson.annotations.SerializedName

data class MovieTvModel (
    val id : Int = -1,
    @SerializedName("original_language")
    val language : String = "",
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
)

data class Cast(
    val name : String,
    @SerializedName("profile_path")
    val profilePath : String
)