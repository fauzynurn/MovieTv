package com.example.movietv.callback

import com.example.movietv.data.model.MovieModel

interface MovieTvCallback<T> {
    fun onClick(id : Long)
}

interface GetMovieTvCallback{
    fun onSuccess(data : List<MovieModel>)
}

interface LoadStateCallback{
    fun onRetry()
}

interface GetFavMovieTvCallback<T>{
    fun onSuccess(data : List<T>)
}

interface GetDetailMovieTvCallback<T>{
    fun onSuccess(data : T)
}