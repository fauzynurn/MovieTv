package com.example.movietv.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Cast(
    @PrimaryKey(autoGenerate = false)
    val name : String,
    @SerializedName("profile_path")
    val profilePath : String
) : Parcelable