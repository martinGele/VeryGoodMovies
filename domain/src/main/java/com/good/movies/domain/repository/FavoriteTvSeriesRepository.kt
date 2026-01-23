package com.good.movies.domain.repository

import com.good.movies.domain.model.FavoriteTvSeries
import kotlinx.coroutines.flow.Flow

interface FavoriteTvSeriesRepository {

    fun getAllFavorites(): Flow<List<FavoriteTvSeries>>

    fun isFavorite(tvId: Int): Flow<Boolean>

    suspend fun addFavorite(tvSeries: FavoriteTvSeries)

    suspend fun removeFavorite(tvId: Int)
}
