package com.example.movietv.data.repository

import androidx.paging.DataSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.movietv.callback.GetDetailMovieTvCallback
import com.example.movietv.callback.GetMovieTvCallback
import com.example.movietv.data.datasource.local.LocalDataSource
import com.example.movietv.data.datasource.room.AppDao
import com.example.movietv.data.datasource.room.RoomDataSource
import com.example.movietv.data.datasource.room.rx.GetFavMovieListRxPagingSource
import com.example.movietv.data.datasource.room.rx.GetFavTvShowListRxPagingSource
import com.example.movietv.data.datasource.room.rx.GetMovieListRxPagingSource
import com.example.movietv.data.model.FavoriteMovieEntity
import com.example.movietv.data.model.MovieTvModel
import io.reactivex.Flowable

class MovieTvRepository(
    val favMovieListDataSource : GetFavMovieListRxPagingSource,
    val favTvShowListDataSource : GetFavTvShowListRxPagingSource,
    val movieListDataSource : GetMovieListRxPagingSource,
    val tvShowListDataSource: GetFavTvShowListRxPagingSource,
    val localDataSource: LocalDataSource,
    val roomDataSource: RoomDataSource
    ) {

    fun getMovie() : Flowable<PagingData<MovieTvModel>> = Pager(
        config = PagingConfig(
            pageSize = 6,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {favMovieListDataSource}
    ).flowable

    fun getTvShow() : Flowable<PagingData<MovieTvModel>> = Pager(
        config = PagingConfig(
            pageSize = 6,
            prefetchDistance = 5,
        ),
        pagingSourceFactory = {favTvShowListDataSource}
    ).flowable

    fun getFavMovie() : Flowable<PagingData<MovieTvModel>> = Pager(
        config = PagingConfig(
            pageSize = 6,
        ),
        pagingSourceFactory = {favMovieListDataSource}
    ).flowable

    fun getFavTvShow() : Flowable<PagingData<MovieTvModel>> = Pager(
        config = PagingConfig(
            pageSize = 6,
        ),
        pagingSourceFactory = {favTvShowListDataSource}
    ).flowable

    fun getDetailMovie(id : Int,callback : GetDetailMovieTvCallback){
        callback.onSuccess(
            localDataSource.getDetailMovie(id) ?: MovieTvModel()
        )
    }

    fun getDetailTvShow(id : Int,callback : GetDetailMovieTvCallback){
        callback.onSuccess(localDataSource.getDetailTvShow(id) ?: MovieTvModel())
    }

    fun addFavoriteMovie(id: Int){
        roomDataSource.addFavoriteMovie(id)
    }

    fun addFavoriteTvShow(id: Int){
        roomDataSource.addFavoriteTvShow(id)
    }

    fun deleteFavoriteMovie(id: Int){
        roomDataSource.deleteFavoriteMovie(id)
    }

    fun deleteFavoriteTvShow(id: Int){
        roomDataSource.deleteFavoriteTvShow(id)
    }

}