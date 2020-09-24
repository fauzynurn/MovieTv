package com.example.movietv.data.mapper

import com.example.movietv.data.entity.MovieEntity
import com.example.movietv.data.entity.TvShowEntity
import com.example.movietv.data.model.MovieModel
import com.example.movietv.data.model.TvShowModel

fun MovieModel.toMovieEntity() : MovieEntity = MovieEntity(
    id = this.id,
    title = this.title,
    posterUrl = this.posterUrl,
    rating = this.rating,
    vote = this.vote
)

fun TvShowModel.toTvShowEntity() : TvShowEntity = TvShowEntity(
    id = this.id,
    title = this.title,
    posterUrl = this.posterUrl,
    rating = this.rating,
    vote = this.vote
)