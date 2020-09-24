package com.example.movietv.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.movietv.utils.converter.ListCastConverter
import com.example.movietv.utils.converter.ListStringConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TvShowModel (
    @PrimaryKey(autoGenerate = false)
    val id : Long = -1,
    @SerializedName("original_language")
    val language : String = "",
    @SerializedName("name")
    val title : String = "",
    @SerializedName("poster_path")
    val posterUrl : String = "",
    @SerializedName("backdrop_path")
    val backdropUrl : String = "",
    @SerializedName("number_of_episodes")
    val totalEpisode : Int = 0,
    @SerializedName("adult")
    val isForAdult : Boolean? = false,
    val overview : String = "",
    val genre : List<Genre> = listOf(),
    @SerializedName("runtime")
    val duration : Int = 0,
    @SerializedName("actor_list")
    val castList : List<Cast>? = listOf(),
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir : Episode? = null,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir : Episode? = null,
    @SerializedName("release_date")
    val releasedDate : String? = "",
    @SerializedName("vote_average")
    val rating : Double? = 0.0,
    @SerializedName("vote_count")
    val vote : Int = 0,
    var isFav : Boolean = false
)