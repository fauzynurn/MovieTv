package com.example.movietv.di

import com.example.movietv.ui.home.MovieTvViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelSourceModule = module {
    viewModel{ MovieTvViewModel(get()) }
}