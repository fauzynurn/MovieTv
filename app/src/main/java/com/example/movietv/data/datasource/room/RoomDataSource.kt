package com.example.movietv.data.datasource.room

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietv.data.model.*
import io.reactivex.Single
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RoomDataSource(val appDao: AppDao) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun setFavoriteMovie(movie : MovieModel) {
        executorService.execute { appDao.setFavoriteMovie(movie) }
    }

    fun setFavoriteTvShow(tvShow : TvShowModel) {
        executorService.execute { appDao.setFavoriteTvShow(tvShow) }
    }

    fun getMovieKeyById(id: Long): MovieRemoteKey? = appDao.getMovieKeyById(id)

    fun getTvShowKeyById(id: Long): TvShowRemoteKey? = appDao.getTvShowKeyById(id)

    fun clearAllMovieKey(){
        executorService.execute { appDao.clearAllMovieKey() }
    }

    fun clearAllTvShowKey(){
        executorService.execute { appDao.clearAllTvShowKey() }
    }

    fun clearAllMovie(){
        executorService.execute { appDao.clearAllMovie() }
    }

    fun clearAllTvShow(){
        executorService.execute { appDao.clearAllTvShow() }
    }

    fun insertAllMovieKey(key: List<MovieRemoteKey>){
        executorService.execute { appDao.insertAllMovieKey(key) }
    }

    fun insertAllTvShowKey(key: List<TvShowRemoteKey>){
        executorService.execute { appDao.insertAllTvShowKey(key) }
    }

    fun insertAllMovie(list : List<MovieModel>) {
        executorService.execute { appDao.insertAllMovie(list) }
    }

    fun insertAllTvShow(list : List<TvShowModel>) {
        executorService.execute { appDao.insertAllTvShow(list) }
    }

    fun getAllMovie() : PagingSource<Int, MovieModel> = appDao.getAllMovie()
    fun getAllTvShow() : PagingSource<Int, TvShowModel> = appDao.getAllTvShow()

    fun getAllFavMovie() : Single<List<FavoriteMovieEntity>?> = appDao.getAllFavMovieId()
    fun getAllFavTvShow() : Single<List<FavoriteTvShowEntity>> = appDao.getAllFavTvShowId()

    fun insertFavoriteMovie(movie : FavoriteMovieEntity){
        executorService.execute { appDao.insertFavoriteMovie(movie)}
    }
    fun insertFavoriteMovie(tvShow : FavoriteTvShowEntity){
        executorService.execute { appDao.insertFavoriteTvShow(tvShow)}
    }
}