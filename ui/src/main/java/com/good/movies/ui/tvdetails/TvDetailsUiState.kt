package com.good.movies.ui.tvdetails

import androidx.compose.runtime.Stable
import com.good.movies.domain.model.TvSeriesDetails

@Stable
data class TvDetailsUiState(
    val isLoading: Boolean = false,
    val tvSeriesDetails: TvSeriesDetails? = null,
    val error: String? = null,
    val isFavorite: Boolean = false
)

sealed interface TvDetailsIntent {
    data object LoadDetails : TvDetailsIntent
    data object ToggleFavorite : TvDetailsIntent
}
