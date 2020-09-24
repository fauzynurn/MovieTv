package com.example.movietv.di

import com.example.movietv.data.datasource.remote.GetMoviesPagingSource
import com.example.movietv.data.datasource.remote.GetTvShowsPagingSource
import com.example.movietv.data.datasource.remote.RemoteDataSource
import com.example.movietv.data.datasource.room.AppRoomDatabase
import com.example.movietv.data.datasource.room.RoomDataSource
import com.example.movietv.data.repository.MovieTvRepository
import org.koin.dsl.module

val dataSourceModule = module {
    single { RemoteDataSource(get())}
    single { AppRoomDatabase.getDatabase(get()) }
    single { get<AppRoomDatabase>().appDao() }
    single { RoomDataSource(get()) }
    single { GetMoviesPagingSource(get()) }
    single { GetTvShowsPagingSource(get()) }
    single { MovieTvRepository(get(),get(),get(),get(),get()) }
}