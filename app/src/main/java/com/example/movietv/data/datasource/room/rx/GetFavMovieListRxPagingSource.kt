package com.example.movietv.data.datasource.room.rx

import androidx.paging.ExperimentalPagingApi
import androidx.paging.rxjava2.RxPagingSource
import com.example.movietv.data.datasource.local.LocalDataSource
import com.example.movietv.data.datasource.room.RoomDataSource
import com.example.movietv.data.model.MovieTvModel
import io.reactivex.schedulers.Schedulers
import io.reactivex.Single

@OptIn(ExperimentalPagingApi::class)
class GetFavMovieListRxPagingSource(
    val size : Int,
    val localDataSource : LocalDataSource, val roomDataSource: RoomDataSource
) : RxPagingSource<Int, MovieTvModel>(){
    fun getResult(data : List<MovieTvModel>, position: Int, totalItem : Int) : LoadResult<Int,MovieTvModel> = LoadResult.Page(
        data = data,
        prevKey = if (position == 0) null else position - 1,
        nextKey = if (position == totalItem) null else position + 1
    )

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieTvModel>> {
        val position = params.key ?: 0
        return localDataSource.getMovieList(position, size)
            .subscribeOn(Schedulers.io())
            .zipWith(
                roomDataSource.getFavoriteMovieId(),
                { movieList,favMovieIdList ->
                    return@zipWith movieList.map {
                            item -> item.isFav = favMovieIdList.any{ fav -> fav.id == item.id}
                        return@map item
                    }
                })
            .subscribeOn(Schedulers.io())
            .map {getResult(it,position,2)}
            .onErrorReturn{LoadResult.Error(it)}
    }
}