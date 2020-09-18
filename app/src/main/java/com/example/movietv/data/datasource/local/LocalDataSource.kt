package com.example.movietv.data.datasource.local

import com.example.movietv.data.datasource.room.AppDao
import com.example.movietv.data.model.*
import com.example.movietv.utils.JsonManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val TOTAL_PAGES = 1

class LocalDataSource(val jsonManager: JsonManager) {
    var savedMovieList : MutableList<MovieModel>? = null
    var savedTvShowList : MutableList<TvShowModel>? = null
    private val listMovieType = object : TypeToken<List<MovieModel>>() {}.type
    private val listTvShowType = object : TypeToken<List<TvShowModel>>() {}.type

    fun getMovieList(page : Int, size : Int): Single<MovieTvResponse<MovieModel>> {
        val jsonFileString = jsonManager.getJsonDataFromAsset("movie.json")
        if(savedMovieList == null){
            savedMovieList = Gson().fromJson(jsonFileString, listMovieType)
        }
        val x = savedMovieList!!.getSubList(page*size,size*(page+1))
        return Single.just(
            MovieTvResponse(
                TOTAL_PAGES,
                page,
                x)
        )
    }

    fun getTvShowList(page : Int, size : Int): Single<MovieTvResponse<TvShowModel>> {
        val jsonFileString = jsonManager.getJsonDataFromAsset("tvshow.json")
        if(savedTvShowList == null){
            savedTvShowList = Gson().fromJson(jsonFileString, listTvShowType)
        }
        return Single.just(
            MovieTvResponse(
                TOTAL_PAGES,
                page,
                savedTvShowList!!.getSubList(page*size,size*(page+1)
            )))
    }

    fun getDetailMovie(id : Long) = savedMovieList?.find { it.id == id }
    fun getDetailTvShow(id : Long) = savedTvShowList?.find { it.id == id }

    fun <T> List<T>.getSubList(page: Int, size: Int) = this.subList(
        Math.min(this.size, page),
        Math.min(this.size, page + size))
}