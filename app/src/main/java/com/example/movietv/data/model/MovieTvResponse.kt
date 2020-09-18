package com.example.movietv.data.model

import com.google.gson.annotations.SerializedName

data class MovieTvResponse<T> (
    @SerializedName("total_pages")
    val totalPages : Int?,
    val page : Int?,
    @SerializedName("results")
    var list : List<T>?
)