package com.good.movies.ui.details

import androidx.compose.runtime.Stable
import com.good.movies.domain.model.MovieDetails

@Stable
data class DetailsUiState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val error: String? = null
)

