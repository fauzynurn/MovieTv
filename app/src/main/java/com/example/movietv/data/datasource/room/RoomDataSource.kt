package com.example.movietv.data.datasource.room

import com.example.movietv.data.model.FavoriteMovieEntity
import com.example.movietv.data.model.FavoriteTvShowEntity
import io.reactivex.Single
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RoomDataSource(val appDao: AppDao) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun addFavoriteMovie(id: Int){
        executorService.execute { appDao.insertFavoriteMovie(FavoriteMovieEntity(id)) }
    }

    fun addFavoriteTvShow(id: Int){
        executorService.execute { appDao.insertFavoriteTvShow(FavoriteTvShowEntity(id)) }
    }

    fun deleteFavoriteMovie(id: Int){
        executorService.execute {appDao.deleteFavoriteMovie(FavoriteMovieEntity(id))}
    }

    fun deleteFavoriteTvShow(id: Int){
        executorService.execute{appDao.deleteFavoriteTvShow(FavoriteTvShowEntity(id))}
    }

    fun getFavoriteMovieId() : Single<List<FavoriteMovieEntity>> = appDao.getFavoriteMovie()

    fun getFavoriteTvShowId() : Single<List<FavoriteTvShowEntity>> = appDao.getFavoriteTvShow()
}