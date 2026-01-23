package com.good.movies.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.good.movies.domain.usecase.GetFavoriteMoviesUseCase
import com.good.movies.domain.usecase.RemoveFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val removeFavoriteMovieUseCase: RemoveFavoriteMovieUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesMoviesUiState())
    val uiState: StateFlow<FavoritesMoviesUiState> = _uiState.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect { favorites ->
                _uiState.update {
                    it.copy(
                        favorites = favorites,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun removeFavorite(movieId: Int) {
        viewModelScope.launch {
            removeFavoriteMovieUseCase(movieId)
        }
    }
}
