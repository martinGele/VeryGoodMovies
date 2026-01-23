package com.good.movies.data.repository

import com.good.movies.data.local.dao.FavoriteMovieDao
import com.good.movies.data.local.entity.toDomain
import com.good.movies.data.local.entity.toEntity
import com.good.movies.domain.model.FavoriteMovie
import com.good.movies.domain.repository.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteMovieRepositoryImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoriteMovieRepository {

    override fun getAllFavorites(): Flow<List<FavoriteMovie>> {
        return favoriteMovieDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> {
        return favoriteMovieDao.isFavorite(movieId)
    }

    override suspend fun addFavorite(movie: FavoriteMovie) {
        favoriteMovieDao.addFavorite(movie.toEntity())
    }

    override suspend fun removeFavorite(movieId: Int) {
        favoriteMovieDao.removeFavorite(movieId)
    }
}
