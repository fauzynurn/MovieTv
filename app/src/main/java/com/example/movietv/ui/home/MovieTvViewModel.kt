package com.example.movietv.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import com.example.movietv.callback.GetDetailMovieTvCallback
import com.example.movietv.data.model.MovieModel
import com.example.movietv.data.model.TvShowModel
import com.example.movietv.data.repository.MovieTvRepository
import io.reactivex.Flowable

class MovieTvViewModel(val repository: MovieTvRepository) : ViewModel() {

    val movie = MutableLiveData<MovieModel>()
    val tvShow = MutableLiveData<TvShowModel>()

    fun getMovieList() : Flowable<PagingData<MovieModel>> = repository.getMovie().cachedIn(viewModelScope)
    fun getTvShowList() : Flowable<PagingData<TvShowModel>> = repository.getTvShow().cachedIn(viewModelScope)

    fun getFavMovieList() : Flowable<PagingData<MovieModel>> = getMovieList().map {
        it.filter {
            it.isFav
        }
    }
    fun getFavTvShowList() : Flowable<PagingData<TvShowModel>> = getTvShowList().map {
        it.filter {
            it.isFav
        }
    }

    fun setMovieAsFav(movie : MovieModel) {
        repository.setFavoriteMovie(movie)
    }

//    fun setTvShowAsFav(id: Int) {
//        repository.addFavoriteTvShow(id)
//    }

    fun getMovie(id : Long) {
        repository.getDetailMovie(id, object : GetDetailMovieTvCallback<MovieModel> {
            override fun onSuccess(data: MovieModel) {
                movie.postValue(data)
            }
        })
    }

    fun getTvShow(id : Long) {
        repository.getDetailTvShow(id, object : GetDetailMovieTvCallback<TvShowModel> {
            override fun onSuccess(data: TvShowModel) {
                tvShow.postValue(data)
            }
        })
    }

}