package com.example.movietv.data.datasource.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movietv.data.model.*

@Database(entities = [
    Cast::class,MovieModel::class,
    TvShowModel::class,
    MovieRemoteKey::class,
    TvShowRemoteKey::class,
    FavoriteMovieEntity::class,
    FavoriteTvShowEntity::class
], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppRoomDatabase {
            if (INSTANCE == null) {
                synchronized(AppRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppRoomDatabase::class.java
                            ,"app_database.db"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE as AppRoomDatabase
        }
    }
}