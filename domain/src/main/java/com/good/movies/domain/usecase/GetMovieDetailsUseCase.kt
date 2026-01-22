package com.good.movies.domain.usecase

import com.good.movies.core.util.NetworkResult
import com.good.movies.domain.model.MovieDetails
import com.good.movies.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): NetworkResult<MovieDetails> {
        return repository.getMovieDetails(movieId)
    }
}
