package com.good.movies.domain.usecase

import androidx.paging.PagingData
import com.good.movies.domain.model.Movie
import com.good.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
)