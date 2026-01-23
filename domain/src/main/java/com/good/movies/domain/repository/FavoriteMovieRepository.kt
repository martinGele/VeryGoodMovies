package com.good.movies.domain.repository

import com.good.movies.domain.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieRepository {

    fun getAllFavorites(): Flow<List<FavoriteMovie>>

    fun isFavorite(movieId: Int): Flow<Boolean>

    suspend fun addFavorite(movie: FavoriteMovie)

    suspend fun removeFavorite(movieId: Int)
}
