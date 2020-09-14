package com.example.movietv

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.multidex.MultiDexApplication
import com.example.movietv.di.commonModule
import com.example.movietv.di.dataSourceModule
import com.example.movietv.di.viewModelSourceModule
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
                    commonModule, dataSourceModule, viewModelSourceModule
                )
            )
        }
        startActivity(Intent(this@MovieTvApplication, MainActivity::class.java).addFlags(FLAG_ACTIVITY_NEW_TASK))
    }
}