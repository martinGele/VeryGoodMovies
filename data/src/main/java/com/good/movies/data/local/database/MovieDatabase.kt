package com.good.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.good.movies.data.local.dao.FavoriteMovieDao
import com.good.movies.data.local.dao.FavoriteTvSeriesDao
import com.good.movies.data.local.entity.FavoriteMovieEntity
import com.good.movies.data.local.entity.FavoriteTvSeriesEntity

@Database(
    entities = [FavoriteMovieEntity::class, FavoriteTvSeriesEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun favoriteTvSeriesDao(): FavoriteTvSeriesDao
}
