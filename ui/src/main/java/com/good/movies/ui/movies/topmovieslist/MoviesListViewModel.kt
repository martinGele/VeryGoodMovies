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

/**
 * ViewModel for fetching and managing the state of top-rated movies.
 */
@HiltViewModel
class MoviesListViewModel @Inject constructor(
    getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesListUiState())
    val uiState: StateFlow<MoviesListUiState> = _uiState.asStateFlow()

    private val topRatedMovies = getTopRatedMoviesUseCase().cachedIn(viewModelScope)

    init {
        loadTopRatedMovies()
    }

    private fun loadTopRatedMovies() {
        _uiState.update {
            it.copy(movies = topRatedMovies)
        }
    }
}
