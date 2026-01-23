package com.good.movies.ui.tvdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.good.movies.core.util.NetworkResult
import com.good.movies.domain.model.FavoriteTvSeries
import com.good.movies.domain.usecase.AddFavoriteTvSeriesUseCase
import com.good.movies.domain.usecase.GetTvSeriesDetailsUseCase
import com.good.movies.domain.usecase.IsFavoriteTvSeriesUseCase
import com.good.movies.domain.usecase.RemoveFavoriteTvSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val getTvSeriesDetailsUseCase: GetTvSeriesDetailsUseCase,
    private val isFavoriteTvSeriesUseCase: IsFavoriteTvSeriesUseCase,
    private val addFavoriteTvSeriesUseCase: AddFavoriteTvSeriesUseCase,
    private val removeFavoriteTvSeriesUseCase: RemoveFavoriteTvSeriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tvId: Int = checkNotNull(savedStateHandle["tvId"])

    private val _uiState = MutableStateFlow(TvDetailsUiState())
    val uiState: StateFlow<TvDetailsUiState> = _uiState.asStateFlow()

    init {
        loadTvSeriesDetails()
        observeFavoriteStatus()
    }

    fun handleIntent(intent: TvDetailsIntent) {
        when (intent) {
            is TvDetailsIntent.LoadDetails -> loadTvSeriesDetails()
            is TvDetailsIntent.ToggleFavorite -> toggleFavorite()
        }
    }

    private fun loadTvSeriesDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getTvSeriesDetailsUseCase(tvId)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, tvSeriesDetails = result.data) }
                }
                is NetworkResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            isFavoriteTvSeriesUseCase(tvId).collect { isFavorite ->
                _uiState.update { it.copy(isFavorite = isFavorite) }
            }
        }
    }

    private fun toggleFavorite() {
        val tvSeriesDetails = _uiState.value.tvSeriesDetails ?: return
        val isFavorite = _uiState.value.isFavorite

        viewModelScope.launch {
            if (isFavorite) {
                removeFavoriteTvSeriesUseCase(tvId)
            } else {
                val favoriteTvSeries = FavoriteTvSeries(
                    id = tvSeriesDetails.id,
                    name = tvSeriesDetails.name,
                    overview = tvSeriesDetails.overview,
                    posterPath = tvSeriesDetails.posterPath,
                    voteAverage = tvSeriesDetails.voteAverage,
                    firstAirDate = tvSeriesDetails.firstAirDate
                )
                addFavoriteTvSeriesUseCase(favoriteTvSeries)
            }
        }
    }
}
