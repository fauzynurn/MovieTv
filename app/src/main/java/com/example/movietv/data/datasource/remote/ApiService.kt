package com.example.movietv.data.datasource.remote

import com.example.movietv.data.model.MovieModel
import com.example.movietv.data.model.MovieTvResponse
import com.example.movietv.data.model.TvShowModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMovieList(
        @Query("api_key") apiKey: String = "21440930b1350cd8b4d28accce3a4799",
        @Query("page") page: Int
    ) : Single<MovieTvResponse<MovieModel>>

    @GET("tv/popular")
    fun getTvShowList(
        @Query("api_key") apiKey: String = "21440930b1350cd8b4d28accce3a4799",
        @Query("page") page: Int
    ) : Single<MovieTvResponse<TvShowModel>>
}
