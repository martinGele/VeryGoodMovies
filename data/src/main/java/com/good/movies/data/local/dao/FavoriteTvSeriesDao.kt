package com.good.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.good.movies.data.local.entity.FavoriteTvSeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTvSeriesDao {

    @Query("SELECT * FROM favorite_tv_series ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteTvSeriesEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_tv_series WHERE id = :tvId)")
    fun isFavorite(tvId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(tvSeries: FavoriteTvSeriesEntity)

    @Query("DELETE FROM favorite_tv_series WHERE id = :tvId")
    suspend fun removeFavorite(tvId: Int)
}
