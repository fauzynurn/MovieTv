package com.example.movietv.data.datasource.remote

import android.annotation.SuppressLint
import android.util.Log
import com.example.movietv.data.model.MovieModel
import com.example.movietv.data.model.TvShowModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RemoteDataSource(val service: ApiService) {
    @SuppressLint("CheckResult")
    fun getDetailMovie(id : Long) : Flowable<ApiResponse<MovieModel>> {
        val resultData = PublishSubject.create<ApiResponse<MovieModel>>()
            service.getDetailMovie(id)
                .flatMap { movie ->
                    service.getMovieCastList(id).map {
                        movie.castList = it.cast
                        movie
                    }
                }
            .subscribeOn(Schedulers.computation())
                .doOnSubscribe { resultData.onNext(ApiResponse.Loading) }
            .subscribe({ response ->
                resultData.onNext(ApiResponse.Success(response))
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getDetailTvShow(id : Long) : Flowable<ApiResponse<TvShowModel>> {
        val resultData = PublishSubject.create<ApiResponse<TvShowModel>>()

            service.getDetailTvShow(id)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { resultData.onNext(ApiResponse.Loading) }
            .subscribe({ response ->
                resultData.onNext(ApiResponse.Success(response))
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}