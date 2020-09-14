package com.example.movietv.di

import com.example.movietv.utils.JsonManager
import org.koin.dsl.module

val commonModule = module {
    single { JsonManager(get()) }
}