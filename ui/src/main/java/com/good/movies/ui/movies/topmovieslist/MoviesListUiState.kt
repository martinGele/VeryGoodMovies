package com.good.movies.ui.movies.topmovieslist

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.good.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MoviesListUiState(
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)
