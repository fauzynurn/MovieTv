package com.example.movietv.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie_remote_key")
data class MovieRemoteKey(
    @PrimaryKey val id: Long,
    val prevKey: Int?,
    val nextKey: Int?
) : Parcelable

@Parcelize
@Entity(tableName = "tvshow_remote_key")
data class TvShowRemoteKey(
    @PrimaryKey val id: Long,
    val prevKey: Int?,
    val nextKey: Int?
) : Parcelable