package com.example.movietv.data.model

import com.google.gson.annotations.SerializedName

data class Episode (
    @SerializedName("air_date")
    val airDate : String = "",
    @SerializedName("episode_number")
    val episodeNumber : Int = 0,
    @SerializedName("name")
    val name : String = "",
    @SerializedName("still_path")
    val stillPath : String = "",
    @SerializedName("vote_average")
    val voteAverage : Double = 0.0,
    @SerializedName("vote_count")
    val voteCount : Int = 0
)