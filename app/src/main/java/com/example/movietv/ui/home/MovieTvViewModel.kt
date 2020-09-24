package com.example.movietv.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import com.example.movietv.callback.GetDetailMovieTvCallback
import com.example.movietv.data.FavoriteAction
import com.example.movietv.data.Resource
import com.example.movietv.data.entity.MovieEntity
import com.example.movietv.data.entity.TvShowEntity
import com.example.movietv.data.model.MovieModel
import com.example.movietv.data.model.TvShowModel
import com.example.movietv.data.repository.MovieTvRepository
import io.reactivex.Flowable

class MovieTvViewModel(val repository: MovieTvRepository) : ViewModel() {

    fun getMovieList() : Flowable<PagingData<MovieEntity>> = repository.getMovie().cachedIn(viewModelScope)
    fun getTvShowList() : Flowable<PagingData<TvShowEntity>> = repository.getTvShow().cachedIn(viewModelScope)

    fun getFavoriteMovieList() : Flowable<PagingData<MovieEntity>> = repository.getFavoriteMovie()

    fun getFavoriteTvShowList() : Flowable<PagingData<TvShowEntity>> = repository.getFavoriteTvShow()

    fun setFavoriteMovie(movie : MovieModel, isFav : Boolean) {
        repository.setFavoriteMovie(movie, isFav)
    }

    fun setFavoriteTvShow(tvShow : TvShowModel, isFav : Boolean) {
        repository.setFavoriteTvShow(tvShow, isFav)
    }

//    fun setTvShowAsFav(id: Int) {
//        repository.addFavoriteTvShow(id)
//    }

    fun getMovie(id : Long) : LiveData<Resource<MovieModel>> = LiveDataReactiveStreams.fromPublisher(repository.getDetailMovie(id))

    fun getTvShow(id : Long) = LiveDataReactiveStreams.fromPublisher(repository.getDetailTvShow(id))

}