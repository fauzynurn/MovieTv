package com.example.movietv.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.movietv.callback.GetDetailMovieTvCallback
import com.example.movietv.data.model.MovieTvModel
import com.example.movietv.data.repository.MovieTvRepository
import io.reactivex.Flowable

class MovieTvViewModel(val repository: MovieTvRepository) : ViewModel() {

    val movieTv = MutableLiveData<MovieTvModel>()

    fun getMovieList() : Flowable<PagingData<MovieTvModel>> = repository.getMovie()
    fun getTvShowList() : Flowable<PagingData<MovieTvModel>> = repository.getTvShow()

    fun getFavMovieList() : Flowable<PagingData<MovieTvModel>> = repository.getFavMovie()
    fun getFavTvShowList() : Flowable<PagingData<MovieTvModel>> = repository.getFavTvShow()

    fun setMovieAsFav(id: Int) {
        repository.addFavoriteMovie(id)
    }

    fun setTvShowAsFav(id: Int) {
        repository.addFavoriteTvShow(id)
    }

    fun getMovie(id : Int) {
        repository.getDetailMovie(id, object : GetDetailMovieTvCallback {
            override fun onSuccess(data: MovieTvModel) {
                movieTv.postValue(data)
            }
        })
    }

    fun getTvShow(id : Int) {
        repository.getDetailTvShow(id, object : GetDetailMovieTvCallback {
            override fun onSuccess(data: MovieTvModel) {
                movieTv.postValue(data)
            }
        })
    }

}