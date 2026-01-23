package com.good.movies.data.repository

import com.good.movies.data.local.dao.FavoriteTvSeriesDao
import com.good.movies.data.local.entity.toDomain
import com.good.movies.data.local.entity.toEntity
import com.good.movies.domain.model.FavoriteTvSeries
import com.good.movies.domain.repository.FavoriteTvSeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteTvSeriesRepositoryImpl @Inject constructor(
    private val favoriteTvSeriesDao: FavoriteTvSeriesDao
) : FavoriteTvSeriesRepository {

    override fun getAllFavorites(): Flow<List<FavoriteTvSeries>> {
        return favoriteTvSeriesDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun isFavorite(tvId: Int): Flow<Boolean> {
        return favoriteTvSeriesDao.isFavorite(tvId)
    }

    override suspend fun addFavorite(tvSeries: FavoriteTvSeries) {
        favoriteTvSeriesDao.addFavorite(tvSeries.toEntity())
    }

    override suspend fun removeFavorite(tvId: Int) {
        favoriteTvSeriesDao.removeFavorite(tvId)
    }
}
