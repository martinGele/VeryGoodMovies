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
import com.good.movies.domain.model.Movie
import com.good.movies.domain.model.TvSeries
import com.good.movies.ui.components.ErrorComponent
import com.good.movies.ui.components.LoadingComponent
import com.good.movies.ui.components.SearchComponent
import com.good.movies.ui.movies.topmovieslist.MovieItem
import com.good.movies.ui.theme.Spacing

/**
 * Screen that displays search functionality for movies and TV series.
 * @param onMovieClick Callback when a movie is clicked, providing the movie ID.
 */
@Composable
fun SearchScreen(
    onMovieClick: (Int) -> Unit,
    onTvSeriesClick: (Int) -> Unit,
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
        onTvSeriesClick = onTvSeriesClick,
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
    onTvSeriesClick: (Int) -> Unit,
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
                            /**
                             * Displays a list of movies using LazyColumn.
                             * Each movie item is represented by the MovieItem composable.
                             * Handles pagination by showing a loading component at the end when more data is being loaded.
                             */
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(Spacing.medium),
                                verticalArrangement = Arrangement.spacedBy(Spacing.small)
                            ) {
                                items(
                                    count = movies.itemCount,
                                    key = { index -> "${movies.peek(index)?.id}_$index" }
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
                            /**
                             * Displays a list of TV series using LazyColumn.
                             * Each TV series item is represented by the TvSeriesItem composable.
                             * Handles pagination by showing a loading component at the end when more data is being loaded.
                             */
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(Spacing.medium),
                                verticalArrangement = Arrangement.spacedBy(Spacing.small)
                            ) {
                                items(
                                    count = tvSeries.itemCount,
                                    key = { index -> "${tvSeries.peek(index)?.id}_$index" }
                                ) { index ->
                                    tvSeries[index]?.let { tv ->
                                        TvSeriesItem(
                                            tvSeries = tv,
                                            onClick = { onTvSeriesClick(tv.id) }
                                        )
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


