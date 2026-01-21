package com.good.movies.ui.movies.topmovieslist

import androidx.paging.PagingData
import com.good.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MoviesUiState(
    val topRatedMovies: Flow<PagingData<Movie>> = emptyFlow()
)
