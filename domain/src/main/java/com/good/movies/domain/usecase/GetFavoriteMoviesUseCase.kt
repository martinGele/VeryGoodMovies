package com.good.movies.domain.usecase

import com.good.movies.domain.model.FavoriteMovie
import com.good.movies.domain.repository.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: FavoriteMovieRepository
) {
    operator fun invoke(): Flow<List<FavoriteMovie>> {
        return repository.getAllFavorites()
    }
}
