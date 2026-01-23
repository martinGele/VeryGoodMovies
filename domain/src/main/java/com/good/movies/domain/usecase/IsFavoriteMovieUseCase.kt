package com.good.movies.domain.usecase

import com.good.movies.domain.repository.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteMovieUseCase @Inject constructor(
    private val repository: FavoriteMovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Boolean> {
        return repository.isFavorite(movieId)
    }
}
