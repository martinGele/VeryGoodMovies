package com.good.movies.ui.moviedetails

import androidx.compose.runtime.Stable
import com.good.movies.domain.model.MovieDetails

@Stable
data class DetailsUiState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val error: String? = null,
    val isFavorite: Boolean = false
)

sealed interface DetailsIntent {
    data object LoadDetails : DetailsIntent
    data object ToggleFavorite : DetailsIntent
}

