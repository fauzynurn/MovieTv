package com.example.movietv.callback

import com.example.movietv.data.model.FavoriteMovieEntity
import com.example.movietv.data.model.FavoriteTvShowEntity
import com.example.movietv.data.model.MovieModel

interface MovieTvCallback<T> {
    fun onClick(id : Long)
    fun onFavIconClicked(item : T, isFav : Boolean)
}

interface GetMovieTvCallback{
    fun onSuccess(data : List<MovieModel>)
}

interface GetFavMovieTvCallback<T>{
    fun onSuccess(data : List<T>)
}

interface GetDetailMovieTvCallback<T>{
    fun onSuccess(data : T)
}