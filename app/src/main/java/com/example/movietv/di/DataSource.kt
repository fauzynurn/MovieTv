package com.example.movietv.di

import com.example.movietv.data.datasource.local.LocalDataSource
import com.example.movietv.data.datasource.room.AppRoomDatabase
import com.example.movietv.data.datasource.room.RoomDataSource
import com.example.movietv.data.datasource.room.rx.GetMovieListRxPagingSource
import com.example.movietv.data.datasource.room.rx.GetTvShowListRxPagingSource
import com.example.movietv.data.repository.MovieTvRepository
import org.koin.dsl.module

val dataSourceModule = module {
    single { LocalDataSource(get()) }
    single { AppRoomDatabase.getDatabase(get()) }
    single { get<AppRoomDatabase>().appDao() }
    single { RoomDataSource(get()) }
    single { GetMovieListRxPagingSource(get(),get(),6,get(),get()) }
    single { GetTvShowListRxPagingSource(get(),get(),6,get(),get()) }
    single { MovieTvRepository(get(),get(),get(),get(),get()) }
}