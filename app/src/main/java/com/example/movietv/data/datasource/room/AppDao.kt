package com.example.movietv.data.datasource.room

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.movietv.data.entity.MovieEntity
import com.example.movietv.data.entity.TvShowEntity
import com.example.movietv.data.model.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface AppDao {

    @Query("SELECT * FROM movieentity")
    fun getAllFavoriteMovie() : PagingSource<Int,MovieEntity>

    @Query("SELECT * FROM tvshowentity")
    fun getAllFavoriteTvShow() :PagingSource<Int,TvShowEntity>

    @Delete
    fun deleteFavoriteMovie(movie : MovieEntity)

    @Delete
    fun deleteFavoriteTvShow(tvShow : TvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie : MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTvShow(tvShow : TvShowEntity)

    @Query("SELECT * FROM movieentity WHERE id = :id")
    fun getFavoriteMovieById(id : Long) : Flowable<List<MovieEntity>>

    @Query("SELECT * FROM tvshowentity WHERE id = :id")
    fun getFavoriteTvShowById(id : Long) : Flowable<List<TvShowEntity>>
}