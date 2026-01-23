package com.good.movies.domain.usecase

import com.good.movies.domain.model.FavoriteMovie
import com.good.movies.domain.repository.FavoriteMovieRepository
import javax.inject.Inject

class AddFavoriteMovieUseCase @Inject constructor(
    private val repository: FavoriteMovieRepository
) {
    suspend operator fun invoke(movie: FavoriteMovie) {
        repository.addFavorite(movie)
    }
}
