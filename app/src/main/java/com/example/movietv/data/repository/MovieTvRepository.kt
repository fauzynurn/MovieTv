package com.example.movietv.data.repository

import android.annotation.SuppressLint
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.movietv.data.Resource
import com.example.movietv.data.datasource.remote.ApiResponse
import com.example.movietv.data.datasource.remote.GetMoviesPagingSource
import com.example.movietv.data.datasource.remote.GetTvShowsPagingSource
import com.example.movietv.data.datasource.remote.RemoteDataSource
import com.example.movietv.data.datasource.room.AppDao
import com.example.movietv.data.datasource.room.RoomDataSource
import com.example.movietv.data.entity.MovieEntity
import com.example.movietv.data.entity.TvShowEntity
import com.example.movietv.data.mapper.toMovieEntity
import com.example.movietv.data.mapper.toTvShowEntity
import com.example.movietv.data.model.MovieModel
import com.example.movietv.data.model.TvShowModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rx.Observable

class MovieTvRepository(
    val dao : AppDao,
    val movieListDataSource : GetMoviesPagingSource,
    val tvShowListDataSource: GetTvShowsPagingSource,
    val remoteDataSource: RemoteDataSource,
    val roomDataSource: RoomDataSource
    ) {

    fun getFavoriteMovie() : Flowable<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            prefetchDistance = 5,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            dao.getAllFavoriteMovie()
        }
    ).flowable

    fun getFavoriteTvShow() : Flowable<PagingData<TvShowEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            prefetchDistance = 5,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            dao.getAllFavoriteTvShow()
        }
    ).flowable

    fun getMovie() : Flowable<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            prefetchDistance = 5,
            initialLoadSize = 20
        )
        ,pagingSourceFactory = {movieListDataSource}
    ).flowable

    fun getTvShow() : Flowable<PagingData<TvShowEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 30,
            prefetchDistance = 5,
            initialLoadSize = 20
        )
        ,pagingSourceFactory = {tvShowListDataSource}
    ).flowable

    @SuppressLint("CheckResult")
    fun getDetailMovie(id : Long) : Flowable<Resource<MovieModel>> {
        val result = PublishSubject.create<Resource<MovieModel>>()
        result.onNext(Resource.Loading(null))

        Flowable.zip(
            remoteDataSource.getDetailMovie(id),roomDataSource.getFavoriteMovieById(id)
        ){
            movie,movieFromDb -> when (movie) {
            is ApiResponse.Success -> {
                movie.data.isFav = movieFromDb.isNotEmpty()
                Resource.Success(movie.data)
            }
            is ApiResponse.Error -> {
                Resource.Error(movie.errorMessage)
            }
            else -> Resource.Error("Error occurred")
        }
        }.subscribe {
            result.onNext(it)
        }
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getDetailTvShow(id : Long) : Flowable<Resource<TvShowModel>> {
        val result = PublishSubject.create<Resource<TvShowModel>>()
        result.onNext(Resource.Loading(null))
        Flowable.zip(
            remoteDataSource.getDetailTvShow(id),roomDataSource.getFavoriteTvShowById(id)
        ){
                tvShow,tvShowFromDb -> when (tvShow) {
            is ApiResponse.Success -> {
                tvShow.data.isFav = tvShowFromDb.isNotEmpty()
                Resource.Success(tvShow.data)
            }
            is ApiResponse.Error -> {
                Resource.Error(tvShow.errorMessage)
            }
            else -> Resource.Error("Error occurred")
        }
        }.subscribe {
            result.onNext(it)
        }
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }


    fun setFavoriteMovie(movie: MovieModel, isFavorite : Boolean){
        if(isFavorite){
            roomDataSource.insertFavoriteMovie(
                movie.toMovieEntity()
            )
        }else{
            roomDataSource.deleteFavoriteMovie(
                movie.toMovieEntity()
            )
        }
    }

    fun setFavoriteTvShow(tvShow: TvShowModel, isFavorite : Boolean){
        if(isFavorite){
            roomDataSource.insertFavoriteTvShow(
                tvShow.toTvShowEntity()
            )
        }else{
            roomDataSource.deleteFavoriteTvShow(
                tvShow.toTvShowEntity()
            )
        }
    }

}