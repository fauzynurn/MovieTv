package com.example.movietv.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.movietv.callback.GetDetailMovieTvCallback
import com.example.movietv.data.datasource.local.LocalDataSource
import com.example.movietv.data.datasource.room.AppDao
import com.example.movietv.data.datasource.room.RoomDataSource
import com.example.movietv.data.datasource.room.rx.GetMovieListRxPagingSource
import com.example.movietv.data.datasource.room.rx.GetTvShowListRxPagingSource
import com.example.movietv.data.model.FavoriteMovieEntity
import com.example.movietv.data.model.MovieModel
import com.example.movietv.data.model.TvShowModel
import io.reactivex.Flowable

class MovieTvRepository(
    val dao : AppDao,
    val movieListDataSource : GetMovieListRxPagingSource,
    val tvShowListDataSource: GetTvShowListRxPagingSource,
    val localDataSource: LocalDataSource,
    val roomDataSource: RoomDataSource
    ) {

    fun getMovie() : Flowable<PagingData<MovieModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 30,
            prefetchDistance = 5,
            initialLoadSize = 20
        ),
        remoteMediator = movieListDataSource
        ,pagingSourceFactory = {roomDataSource.getAllMovie()}
    ).flowable

    fun getTvShow() : Flowable<PagingData<TvShowModel>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 30,
            prefetchDistance = 5,
            initialLoadSize = 20
        ),
        remoteMediator = tvShowListDataSource
        ,pagingSourceFactory = {roomDataSource.getAllTvShow()}
    ).flowable

    fun getDetailMovie(id : Long,callback : GetDetailMovieTvCallback<MovieModel>){
        callback.onSuccess(
            localDataSource.getDetailMovie(id) ?: MovieModel()
        )
    }

    fun getDetailTvShow(id : Long,callback : GetDetailMovieTvCallback<TvShowModel>){
        callback.onSuccess(localDataSource.getDetailTvShow(id) ?: TvShowModel())
    }

    fun setFavoriteMovie(movie: MovieModel){
        movie.isFav = !movie.isFav
        if(movie.isFav) roomDataSource.addFavMovie(movie) else roomDataSource.deleteFavMovie(movie)
    }

//    fun addFavoriteTvShow(id: Int){
//        roomDataSource.addFavoriteTvShow(id)
//    }
//
//    fun deleteFavoriteMovie(id: Int){
//        roomDataSource.deleteFavoriteMovie(id)
//    }
//
//    fun deleteFavoriteTvShow(id: Int){
//        roomDataSource.deleteFavoriteTvShow(id)
//    }

}