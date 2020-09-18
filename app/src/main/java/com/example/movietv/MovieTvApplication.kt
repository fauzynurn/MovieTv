package com.example.movietv

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.multidex.MultiDexApplication
import com.example.movietv.di.*
import com.example.movietv.ui.home.MainActivity
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieTvApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        //initialize koin
        startKoin {
            androidContext(this@MovieTvApplication)
            modules(
                listOf(
                    commonModule, dataSourceModule, viewModelSourceModule, networkSource
                )
            )
        }
        startActivity(Intent(this@MovieTvApplication, MainActivity::class.java)
            .addFlags(FLAG_ACTIVITY_SINGLE_TOP)
            .addFlags(FLAG_ACTIVITY_NEW_TASK))
    }
}