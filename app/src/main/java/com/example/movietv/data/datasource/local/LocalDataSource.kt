package com.example.movietv.data.datasource.local

import com.example.movietv.data.datasource.room.AppDao
import com.example.movietv.data.model.FavoriteMovieEntity
import com.example.movietv.data.model.FavoriteTvShowEntity
import com.example.movietv.data.model.MovieTvModel
import com.example.movietv.utils.JsonManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalDataSource(val jsonManager: JsonManager) {
    var savedMovieList : MutableList<MovieTvModel>? = null
    var savedTvShowList : MutableList<MovieTvModel>? = null
    private val listPersonType = object : TypeToken<List<MovieTvModel>>() {}.type

    fun getMovieList(page : Int, size : Int): Single<List<MovieTvModel>> {
        val jsonFileString = jsonManager.getJsonDataFromAsset("movie.json")
        if(savedMovieList == null){
            savedMovieList = Gson().fromJson(jsonFileString, listPersonType)
        }
        return Single.just(savedMovieList!!.getSubList(page*size,size*(page+1)))
    }

    fun getTvShowList(page : Int, size : Int): Single<List<MovieTvModel>> {
        val jsonFileString = jsonManager.getJsonDataFromAsset("tvshow.json")
        if(savedTvShowList == null){
            savedTvShowList = Gson().fromJson(jsonFileString, listPersonType)
        }
        return Single.just(savedTvShowList!!.getSubList(page*size,size*(page+1)))
    }

    fun getDetailMovie(id : Int) = savedMovieList?.find { it.id == id }
    fun getDetailTvShow(id : Int) = savedTvShowList?.find { it.id == id }

    fun List<MovieTvModel>.getSubList(page: Int, size: Int) = this.subList(
        Math.min(this.size, page),
        Math.min(this.size, page + size))
}