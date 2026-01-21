package com.good.movies.ui.movies.topmovieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.good.movies.domain.usecase.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        handleIntent(MoviesIntent.LoadTopRatedMovies)
    }

    fun handleIntent(intent: MoviesIntent) {
        when (intent) {
            is MoviesIntent.LoadTopRatedMovies -> loadTopRatedMovies()
        }
    }

    private fun loadTopRatedMovies() {
        val movies = getTopRatedMoviesUseCase().cachedIn(viewModelScope)
        _uiState.update { it.copy(topRatedMovies = movies) }
    }
}
