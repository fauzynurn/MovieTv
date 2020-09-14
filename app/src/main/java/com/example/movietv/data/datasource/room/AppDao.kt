package com.example.movietv.data.datasource.room

import androidx.paging.DataSource
import androidx.room.*
import com.example.movietv.data.model.FavoriteMovieEntity
import com.example.movietv.data.model.FavoriteTvShowEntity
import io.reactivex.Single

@Dao
interface AppDao {
    @Insert
    fun insertFavoriteMovie(favMovie : FavoriteMovieEntity)

    @Insert
    fun insertFavoriteTvShow(favTvShow: FavoriteTvShowEntity)

    @Query("SELECT * FROM favoritemovieentity")
    fun getFavoriteMovie() : Single<List<FavoriteMovieEntity>>

    @Query("SELECT * FROM favoritetvshowentity")
    fun getFavoriteTvShow() : Single<List<FavoriteTvShowEntity>>

    @Delete
    fun deleteFavoriteMovie(favMovie : FavoriteMovieEntity)

    @Delete
    fun deleteFavoriteTvShow(favTvShow: FavoriteTvShowEntity)
}