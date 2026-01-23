package com.good.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.good.movies.data.local.dao.FavoriteMovieDao
import com.good.movies.data.local.entity.FavoriteMovieEntity

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
