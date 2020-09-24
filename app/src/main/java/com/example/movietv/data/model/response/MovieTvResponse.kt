package com.example.movietv.data.model.response

import com.example.movietv.data.entity.MovieEntity
import com.example.movietv.data.entity.TvShowEntity
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("total_pages")
    val totalPages : Int,
    val page : Int,
    @SerializedName("results")
    var list : List<MovieEntity>
)

data class TvShowResponse(
    @SerializedName("total_pages")
    val totalPages : Int,
    val page : Int,
    @SerializedName("results")
    var list : List<TvShowEntity>
)