package com.example.movietv.data.datasource.remote

import com.example.movietv.data.model.MovieModel
import com.example.movietv.data.model.response.MovieResponse
import com.example.movietv.data.model.TvShowModel
import com.example.movietv.data.model.response.CastResponse
import com.example.movietv.data.model.response.TvShowResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMovieList(
        @Query("api_key") apiKey: String = "21440930b1350cd8b4d28accce3a4799",
        @Query("page") page: Int
    ) : Single<MovieResponse>

    @GET("tv/popular")
    fun getTvShowList(
        @Query("api_key") apiKey: String = "21440930b1350cd8b4d28accce3a4799",
        @Query("page") page: Int
    ) : Single<TvShowResponse>

    @GET("movie/{movieId}")
    fun getDetailMovie(@Path("movieId") id : Long,
                       @Query("api_key") apiKey: String = "21440930b1350cd8b4d28accce3a4799") : Flowable<MovieModel>

    @GET("tv/{tvShowId}")
    fun getDetailTvShow(@Path("tvShowId") id : Long,
                       @Query("api_key") apiKey: String = "21440930b1350cd8b4d28accce3a4799") : Flowable<TvShowModel>

    @GET("movie/{movieId}/credits")
    fun getMovieCastList(@Path("movieId") id : Long,
                    @Query("api_key") apiKey: String = "21440930b1350cd8b4d28accce3a4799") : Flowable<CastResponse>

    @GET("tv/{tvShowId}/credits")
    fun getTvShowCastList(@Path("tvShowId") id : Long,
                    @Query("api_key") apiKey: String = "21440930b1350cd8b4d28accce3a4799") : Flowable<CastResponse>
}
