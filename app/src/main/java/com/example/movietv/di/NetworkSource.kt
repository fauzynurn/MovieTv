package com.example.movietv.di

import com.example.movietv.data.datasource.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "https://api.themoviedb.org/3/"
val networkSource = module {
    single{provideRetrofit()}
}

fun provideRetrofit(): ApiService {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC

    val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()

    return retrofit.create(ApiService::class.java)
}