package com.good.movies.ui.movies.topmovieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.good.movies.domain.usecase.GetTopRatedMoviesUseCase
import com.good.movies.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    private val topRatedMovies = getTopRatedMoviesUseCase().cachedIn(viewModelScope)

    init {
        handleIntent(MoviesIntent.LoadTopRatedMovies)
    }

    fun handleIntent(intent: MoviesIntent) {
        when (intent) {
            is MoviesIntent.LoadTopRatedMovies -> loadTopRatedMovies()
            is MoviesIntent.Search -> searchMovies(intent.query)
            is MoviesIntent.ClearSearch -> clearSearch()
        }
    }

    private fun loadTopRatedMovies() {
        _uiState.update {
            it.copy(
                movies = topRatedMovies,
            )
        }
    }

    private fun searchMovies(query: String) {
        if (query.isBlank()) {
            clearSearch()
            return
        }
        val searchResults = searchMoviesUseCase(query).cachedIn(viewModelScope)
        _uiState.update {
            it.copy(
                movies = searchResults,
                searchQuery = query,
            )
        }
    }

    private fun clearSearch() {
        _uiState.update {
            it.copy(
                movies = topRatedMovies, searchQuery = ""
            )
        }
    }
}
