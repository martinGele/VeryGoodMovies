package com.good.movies.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.good.movies.domain.model.Movie
import com.good.movies.domain.model.TvSeries
import com.good.movies.domain.usecase.SearchMoviesUseCase
import com.good.movies.domain.usecase.SearchTvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel for handling movie and TV series search functionality.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchTvUseCase: SearchTvUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.Search -> search(intent.query)
            is SearchIntent.ClearSearch -> clearSearch()
            is SearchIntent.SelectContentType -> selectContentType(intent.contentType)
        }
    }

    private fun search(query: String) {
        if (query.isBlank()) {
            clearSearch()
            return
        }

        val currentState = _uiState.value
        when (currentState.contentType) {
            ContentType.MOVIES -> {
                val searchResults = searchMoviesUseCase(query).cachedIn(viewModelScope)
                _uiState.update {
                    it.copy(
                        movies = searchResults,
                        tvSeries = emptyFlow(),
                        searchQuery = query
                    )
                }
            }
            ContentType.TV_SERIES -> {
                val searchResults = searchTvUseCase(query).cachedIn(viewModelScope)
                _uiState.update {
                    it.copy(
                        movies = emptyFlow(),
                        tvSeries = searchResults,
                        searchQuery = query
                    )
                }
            }
        }
    }

    private fun selectContentType(contentType: ContentType) {
        val currentQuery = _uiState.value.searchQuery
        _uiState.update {
            it.copy(
                contentType = contentType,
                movies = emptyFlow(),
                tvSeries = emptyFlow()
            )
        }
        if (currentQuery.isNotBlank()) {
            search(currentQuery)
        }
    }

    private fun clearSearch() {
        _uiState.update {
            it.copy(
                movies = emptyFlow(),
                tvSeries = emptyFlow(),
                searchQuery = ""
            )
        }
    }
}


