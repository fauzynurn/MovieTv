package com.example.movietv.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FavoriteMovieEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id : Long = 0
) : Parcelable