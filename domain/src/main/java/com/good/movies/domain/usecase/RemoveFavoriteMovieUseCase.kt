package com.good.movies.domain.usecase

import com.good.movies.domain.repository.FavoriteMovieRepository
import javax.inject.Inject

class RemoveFavoriteMovieUseCase @Inject constructor(
    private val repository: FavoriteMovieRepository
) {
    suspend operator fun invoke(movieId: Int) {
        repository.removeFavorite(movieId)
    }
}
