package com.example.movietv.data.datasource.remote

import androidx.paging.rxjava2.RxPagingSource
import com.example.movietv.data.entity.TvShowEntity
import com.example.movietv.data.model.response.TvShowResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetTvShowsPagingSource(
    private val service: ApiService
) : RxPagingSource<Int, TvShowEntity>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, TvShowEntity>> {
        val page = params.key ?: 1

        return service.getTvShowList(page = page)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, page) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: TvShowResponse, position: Int): LoadResult<Int, TvShowEntity> {
        return LoadResult.Page(
            data = data.list,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.totalPages) null else position + 1
        )
    }
}