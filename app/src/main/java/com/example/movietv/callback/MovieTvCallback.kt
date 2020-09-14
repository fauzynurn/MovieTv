package com.example.movietv.callback

import com.example.movietv.data.model.FavoriteMovieEntity
import com.example.movietv.data.model.FavoriteTvShowEntity
import com.example.movietv.data.model.MovieTvModel

interface MovieTvCallback {
    fun onClick(id : Int)
    fun onFavIconClicked(id: Int, isFav : Boolean)
}

interface GetMovieTvCallback{
    fun onSuccess(data : List<MovieTvModel>)
}

interface GetFavMovieTvCallback<T>{
    fun onSuccess(data : List<T>)
}

interface GetDetailMovieTvCallback{
    fun onSuccess(data : MovieTvModel)
}