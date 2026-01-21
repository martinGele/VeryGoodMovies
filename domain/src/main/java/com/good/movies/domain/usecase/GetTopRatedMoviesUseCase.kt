package com.good.movies.domain.usecase

import androidx.paging.PagingData
import com.good.movies.domain.model.Movie
import com.good.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return repository.getTopRatedMovies()
    }
}
