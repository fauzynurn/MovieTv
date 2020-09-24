package com.example.movietv.data.datasource.remote

import androidx.paging.rxjava2.RxPagingSource
import com.example.movietv.data.entity.MovieEntity
import com.example.movietv.data.model.response.MovieResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetMoviesPagingSource(
    private val service: ApiService
) : RxPagingSource<Int, MovieEntity>() {


    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieEntity>> {
        val page = params.key ?: 1

        return service.getMovieList(page = page)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, page) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: MovieResponse, position: Int): LoadResult<Int, MovieEntity> {
        return LoadResult.Page(
            data = data.list,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.totalPages) null else position + 1
        )
    }
}