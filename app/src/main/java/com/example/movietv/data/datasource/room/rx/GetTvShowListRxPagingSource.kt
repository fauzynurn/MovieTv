package com.example.movietv.data.datasource.room.rx

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.example.movietv.data.datasource.local.LocalDataSource
import com.example.movietv.data.datasource.remote.ApiService
import com.example.movietv.data.datasource.room.AppRoomDatabase
import com.example.movietv.data.datasource.room.RoomDataSource
import com.example.movietv.data.model.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.Single
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class GetTvShowListRxPagingSource(
    val appRoomDatabase: AppRoomDatabase,
    val service : ApiService,
    val size : Int,
    val localDataSource : LocalDataSource, val roomDataSource: RoomDataSource
) : RxRemoteMediator<Int, TvShowModel>(){
//    fun getResult(data : List<MovieModel>, position: Int, totalItem : Int) : LoadResult<Int,MovieModel> = LoadResult.Page(
//        data = data,
//        prevKey = if (position == 0) null else position - 1,
//        nextKey = if (position == totalItem) null else position + 1
//    )
//
//    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieModel>> {
//        val position = params.key ?: 0
//        return roomDataSource.get(position, size)
//            .subscribeOn(Schedulers.io())
//            .zipWith(
//                roomDataSource.getFavoriteMovieId(),
//                { movieList,favMovieIdList ->
//                    return@zipWith movieList.map {
//                            item -> item.isFav = favMovieIdList.any{ fav -> fav.id == item.id}
//                        return@map item
//                    }
//                })
//            .subscribeOn(Schedulers.io())
//            .map {getResult(it,position,2)}
//            .onErrorReturn{LoadResult.Error(it)}
//    f

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, TvShowModel>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    service.getTvShowList(
                        page = page).zipWith(
                        roomDataSource.getAllFavTvShow(), { tvShowResponse, favMovieIdList ->
                            val mergedList = tvShowResponse.list?.map { item ->
                                item.isFav = favMovieIdList.any { fav -> fav.id == item.id }
                                return@map item
                            }
                            tvShowResponse.list = mergedList
                            return@zipWith tvShowResponse
                        })
                        .map { insertToDb(page, loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.page == it.totalPages) }
                        .onErrorReturn { MediatorResult.Error(it) }

                    //roomDataSource.get(position, size)
//            .subscribeOn(Schedulers.io())
//            .zipWith(
//                roomDataSource.getFavoriteMovieId(),
//                { movieList,favMovieIdList ->
//                    return@zipWith movieList.map {
//                            item -> item.isFav = favMovieIdList.any{ fav -> fav.id == item.id}
//                        return@map item
//                    }
//                })
//            .subscribeOn(Schedulers.io())
//            .map {getResult(it,position,2)}
//            .onErrorReturn{LoadResult.Error(it)}
                }

            }
            .onErrorReturn {
                Log.i("WOY ADA ERROR HEHE",it.message!!)
                MediatorResult.Error(it) }
    }

    private fun insertToDb(page: Int, loadType: LoadType, data: MovieTvResponse<TvShowModel>): MovieTvResponse<TvShowModel> {
        appRoomDatabase.beginTransaction()
        try {
            if (loadType == LoadType.REFRESH) {
                roomDataSource.clearAllTvShowKey()
                roomDataSource.clearAllTvShow()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.page == data.totalPages) null else page + 1
            val keys = data.list?.map {
                TvShowRemoteKey(id = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            keys?.let { roomDataSource.insertAllTvShowKey(it) }
            data.list?.let { roomDataSource.insertAllTvShow(it) }
            appRoomDatabase.setTransactionSuccessful()
            //appRoomDatabase..insertAllMovieKey(keys)
            //roomDataSource.insertAllMovie(data.list)

        } finally {
            appRoomDatabase.endTransaction()
        }
        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, TvShowModel>): TvShowRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { model ->
            roomDataSource.getTvShowKeyById(model.id)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, TvShowModel>):TvShowRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { model ->
            roomDataSource.getTvShowKeyById(model.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TvShowModel>): TvShowRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                roomDataSource.getTvShowKeyById(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }
}