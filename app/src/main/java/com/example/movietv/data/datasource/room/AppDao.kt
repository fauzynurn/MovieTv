package com.example.movietv.data.datasource.room

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*
import com.example.movietv.data.model.*
import io.reactivex.Single

@Dao
interface AppDao {

    @Query("SELECT * FROM moviemodel")
    fun getAllMovie() : PagingSource<Int,MovieModel>

    @Query("SELECT * FROM tvshowmodel")
    fun getAllTvShow() : PagingSource<Int,TvShowModel>

    @Query("SELECT * FROM favoritemovieentity")
    fun getAllFavMovieId() : Single<List<FavoriteMovieEntity>?>

    @Query("SELECT * FROM favoritetvshowentity")
    fun getAllFavTvShowId() : Single<List<FavoriteTvShowEntity>>

    @Query("DELETE FROM moviemodel")
    fun clearAllMovie()

    @Query("DELETE FROM tvshowmodel")
    fun clearAllTvShow()

    @Delete
    fun deleteFavoriteMovie(movie : FavoriteMovieEntity)

    @Delete
    fun deleteFavoriteTvShow(tvShow : FavoriteTvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovie(list : List<MovieModel>) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTvShow(list : List<TvShowModel>)

    @Update
    fun setFavoriteMovie(favMovie : MovieModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(favMovie : FavoriteMovieEntity)

    @Update
    fun setFavoriteTvShow(favTvShow: TvShowModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTvShow(favTvShow : FavoriteTvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovieKey(key: List<MovieRemoteKey>)

    @Query("SELECT * FROM movie_remote_key WHERE id = :movieId")
    fun getMovieKeyById(movieId: Long): MovieRemoteKey?

    @Query("DELETE FROM movie_remote_key")
    fun clearAllMovieKey()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTvShowKey(key: List<TvShowRemoteKey>)

    @Query("SELECT * FROM tvshow_remote_key WHERE id = :tvShowId")
    fun getTvShowKeyById(tvShowId: Long): TvShowRemoteKey?

    @Query("DELETE FROM tvshow_remote_key")
    fun clearAllTvShowKey()
}