package com.example.movietv.data.datasource.room

import androidx.paging.PagingSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.movietv.data.entity.MovieEntity
import com.example.movietv.data.entity.TvShowEntity
import com.example.movietv.data.model.*
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RoomDataSource(val appDao: AppDao) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun insertFavoriteMovie(movie : MovieEntity) {
        executorService.execute {
            appDao.insertFavoriteMovie(movie)
        }
    }

    fun insertFavoriteTvShow(tvShow : TvShowEntity) {
        executorService.execute {
            appDao.insertFavoriteTvShow(tvShow)
        }
    }

    fun deleteFavoriteMovie(movie : MovieEntity) {
        executorService.execute {
            appDao.deleteFavoriteMovie(movie)
        }
    }

    fun deleteFavoriteTvShow(tvShow : TvShowEntity) {
        executorService.execute {
            appDao.deleteFavoriteTvShow(tvShow)
        }
    }

    fun getFavoriteMovieById(id : Long) : Flowable<List<MovieEntity>> = appDao.getFavoriteMovieById(id)

    fun getFavoriteTvShowById(id : Long) : Flowable<List<TvShowEntity>> = appDao.getFavoriteTvShowById(id)

    fun getAllFavoriteMovie() : PagingSource<Int,MovieEntity> = appDao.getAllFavoriteMovie()
    fun getAllFavoriteTvShow() : PagingSource<Int,TvShowEntity> = appDao.getAllFavoriteTvShow()
}