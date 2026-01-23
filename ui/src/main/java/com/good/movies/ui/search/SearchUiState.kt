package com.good.movies.ui.search

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.good.movies.domain.model.Movie
import com.good.movies.domain.model.TvSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

enum class ContentType {
    MOVIES,
    TV_SERIES
}

@Stable
data class SearchUiState(
    val movies: Flow<PagingData<Movie>> = emptyFlow(),
    val tvSeries: Flow<PagingData<TvSeries>> = emptyFlow(),
    val searchQuery: String = "",
    val contentType: ContentType = ContentType.MOVIES
)

sealed interface SearchIntent {
    data class Search(val query: String) : SearchIntent
    data object ClearSearch : SearchIntent
    data class SelectContentType(val contentType: ContentType) : SearchIntent
}
