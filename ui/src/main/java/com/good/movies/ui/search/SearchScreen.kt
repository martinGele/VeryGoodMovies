package com.good.movies.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.good.movies.domain.model.Movie
import com.good.movies.domain.model.TvSeries
import com.good.movies.ui.components.ErrorComponent
import com.good.movies.ui.components.LoadingComponent
import com.good.movies.ui.components.SearchComponent
import com.good.movies.ui.movies.topmovieslist.MovieItem
import com.good.movies.ui.theme.Spacing

@Composable
fun SearchScreen(
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchTextState = rememberTextFieldState()
    val movies = uiState.movies.collectAsLazyPagingItems()
    val tvSeries = uiState.tvSeries.collectAsLazyPagingItems()

    SearchContent(
        searchTextState = searchTextState,
        searchQuery = uiState.searchQuery,
        contentType = uiState.contentType,
        movies = movies,
        tvSeries = tvSeries,
        onIntent = viewModel::handleIntent,
        onMovieClick = onMovieClick,
        modifier = modifier
    )
}

@Composable
private fun SearchContent(
    searchTextState: TextFieldState,
    searchQuery: String,
    contentType: ContentType,
    movies: LazyPagingItems<Movie>,
    tvSeries: LazyPagingItems<TvSeries>,
    onIntent: (SearchIntent) -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchComponent(
            textFieldState = searchTextState,
            onSearchChange = { query ->
                if (query.isNotEmpty()) {
                    onIntent(SearchIntent.Search(query))
                } else {
                    onIntent(SearchIntent.ClearSearch)
                }
            },
            modifier = Modifier.padding(horizontal = Spacing.medium, vertical = Spacing.small)
        )

        ContentTypeSelector(
            selectedContentType = contentType,
            onContentTypeSelected = { onIntent(SearchIntent.SelectContentType(it)) },
            modifier = Modifier.padding(horizontal = Spacing.medium)
        )

        if (searchQuery.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val searchText = when (contentType) {
                    ContentType.MOVIES -> "Search for movies"
                    ContentType.TV_SERIES -> "Search for TV series"
                }
                Text(
                    text = searchText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            when (contentType) {
                ContentType.MOVIES -> {
                    SearchResultsList(
                        loadState = movies.loadState.refresh,
                        itemCount = movies.itemCount,
                        onRetry = { movies.retry() },
                        emptyMessage = "No movies found",
                        content = {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(Spacing.medium),
                                verticalArrangement = Arrangement.spacedBy(Spacing.small)
                            ) {
                                items(
                                    count = movies.itemCount,
                                    key = movies.itemKey { it.id }
                                ) { index ->
                                    movies[index]?.let { movie ->
                                        MovieItem(
                                            movie = movie,
                                            onClick = { onMovieClick(movie.id) }
                                        )
                                    }
                                }

                                if (movies.loadState.append is LoadState.Loading) {
                                    item {
                                        LoadingComponent()
                                    }
                                }
                            }
                        }
                    )
                }

                ContentType.TV_SERIES -> {
                    SearchResultsList(
                        loadState = tvSeries.loadState.refresh,
                        itemCount = tvSeries.itemCount,
                        onRetry = { tvSeries.retry() },
                        emptyMessage = "No TV series found",
                        content = {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(Spacing.medium),
                                verticalArrangement = Arrangement.spacedBy(Spacing.small)
                            ) {
                                items(
                                    count = tvSeries.itemCount,
                                    key = tvSeries.itemKey { it.id }
                                ) { index ->
                                    tvSeries[index]?.let { tv ->
                                        TvSeriesItem(tvSeries = tv)
                                    }
                                }

                                if (tvSeries.loadState.append is LoadState.Loading) {
                                    item {
                                        LoadingComponent()
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ContentTypeSelector(
    selectedContentType: ContentType,
    onContentTypeSelected: (ContentType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.small)
    ) {
        FilterChip(
            selected = selectedContentType == ContentType.MOVIES,
            onClick = { onContentTypeSelected(ContentType.MOVIES) },
            label = { Text("Movies") }
        )
        FilterChip(
            selected = selectedContentType == ContentType.TV_SERIES,
            onClick = { onContentTypeSelected(ContentType.TV_SERIES) },
            label = { Text("TV Series") }
        )
    }
}

@Composable
private fun SearchResultsList(
    loadState: LoadState,
    itemCount: Int,
    onRetry: () -> Unit,
    emptyMessage: String,
    content: @Composable () -> Unit
) {
    when (loadState) {
        is LoadState.Loading -> {
            LoadingComponent()
        }

        is LoadState.Error -> {
            ErrorComponent(
                message = loadState.error.message ?: "Unknown error",
                onRetry = onRetry
            )
        }

        is LoadState.NotLoading -> {
            if (itemCount == 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = emptyMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                content()
            }
        }
    }
}