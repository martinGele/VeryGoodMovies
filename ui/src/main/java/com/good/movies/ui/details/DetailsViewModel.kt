package com.good.movies.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.good.movies.core.util.NetworkResult
import com.good.movies.domain.model.FavoriteMovie
import com.good.movies.domain.usecase.AddFavoriteMovieUseCase
import com.good.movies.domain.usecase.GetMovieDetailsUseCase
import com.good.movies.domain.usecase.IsFavoriteMovieUseCase
import com.good.movies.domain.usecase.RemoveFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val isFavoriteMovieUseCase: IsFavoriteMovieUseCase,
    private val addFavoriteMovieUseCase: AddFavoriteMovieUseCase,
    private val removeFavoriteMovieUseCase: RemoveFavoriteMovieUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetails()
        observeFavoriteStatus()
    }

    fun loadMovieDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getMovieDetailsUseCase(movieId)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, movieDetails = result.data) }
                }
                is NetworkResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            isFavoriteMovieUseCase(movieId).collect { isFavorite ->
                _uiState.update { it.copy(isFavorite = isFavorite) }
            }
        }
    }

    fun toggleFavorite() {
        val movieDetails = _uiState.value.movieDetails ?: return
        val isFavorite = _uiState.value.isFavorite

        viewModelScope.launch {
            if (isFavorite) {
                removeFavoriteMovieUseCase(movieId)
            } else {
                val favoriteMovie = FavoriteMovie(
                    id = movieDetails.id,
                    title = movieDetails.title,
                    overview = movieDetails.overview,
                    posterPath = movieDetails.posterPath,
                    voteAverage = movieDetails.voteAverage,
                    releaseDate = movieDetails.releaseDate
                )
                addFavoriteMovieUseCase(favoriteMovie)
            }
        }
    }
}
