package com.good.movies.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.good.movies.domain.usecase.GetFavoriteMoviesUseCase
import com.good.movies.domain.usecase.GetFavoriteTvSeriesUseCase
import com.good.movies.domain.usecase.RemoveFavoriteMovieUseCase
import com.good.movies.domain.usecase.RemoveFavoriteTvSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getFavoriteTvSeriesUseCase: GetFavoriteTvSeriesUseCase,
    private val removeFavoriteMovieUseCase: RemoveFavoriteMovieUseCase,
    private val removeFavoriteTvSeriesUseCase: RemoveFavoriteTvSeriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        observeFavorites()
    }

    fun handleIntent(intent: FavoritesIntent) {
        when (intent) {
            is FavoritesIntent.RemoveFavoriteMovie -> removeFavoriteMovie(intent.movieId)
            is FavoritesIntent.RemoveFavoriteTvSeries -> removeFavoriteTvSeries(intent.tvId)
            is FavoritesIntent.LoadFavorites -> observeFavorites()
        }
    }

    private fun observeFavorites() {
        /**
         * Combine favorite movies and TV series into a single list and update the UI state.
         * The favorites are mapped to a common FavoriteItem type for display.
         */
        viewModelScope.launch {
            combine(
                getFavoriteMoviesUseCase(),
                getFavoriteTvSeriesUseCase()
            ) { movies, tvSeries ->
                /**
                 * Map favorite movies and TV series to FavoriteItem and combine them into a single list.
                 * mapping each favorite to its respective FavoriteItem type.
                 */
                val movieItems = movies.map { it.toFavoriteItem() }
                val tvItems = tvSeries.map { it.toFavoriteItem() }
                movieItems + tvItems
            }.collect { favorites ->
                _uiState.update {
                    it.copy(
                        favorites = favorites,
                        isLoading = false
                    )
                }
            }
        }
    }

    /**
     * Removes a movie from the favorites list by its ID.
     */
    private fun removeFavoriteMovie(movieId: Int) {
        viewModelScope.launch {
            removeFavoriteMovieUseCase(movieId)
        }
    }

    /**
     * Removes a TV series from the favorites list by its ID.
     */
    private fun removeFavoriteTvSeries(tvId: Int) {
        viewModelScope.launch {
            removeFavoriteTvSeriesUseCase(tvId)
        }
    }
}
