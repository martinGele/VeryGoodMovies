package com.good.movies.ui.movies.topmovieslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.good.movies.domain.model.Movie
import com.good.movies.ui.movies.components.ErrorComponent
import com.good.movies.ui.movies.components.LoadingComponent
import com.good.movies.ui.movies.components.SearchComponent
import com.good.movies.ui.theme.Spacing

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchTextState = rememberTextFieldState()
    val movies = uiState.movies.collectAsLazyPagingItems()

    MoviesScreenContent(
        movies = movies,
        searchTextState = searchTextState,
        onIntent = viewModel::handleIntent,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesScreenContent(
    movies: LazyPagingItems<Movie>,
    searchTextState: TextFieldState,
    onIntent: (MoviesIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchComponent(
            textFieldState = searchTextState,
            onSearchChange = { query ->
                if (query.isNotEmpty()) {
                    onIntent(MoviesIntent.Search(query))
                } else {
                    onIntent(MoviesIntent.ClearSearch)
                }
            },
            modifier = Modifier.padding(horizontal = Spacing.medium, vertical = Spacing.small)
        )

        PullToRefreshBox(
            isRefreshing = movies.loadState.refresh is LoadState.Loading,
            onRefresh = { movies.refresh() },
            modifier = Modifier.fillMaxSize()
        ) {
            when (val refreshState = movies.loadState.refresh) {
                is LoadState.Loading -> {
                    LoadingComponent()
                }
                is LoadState.Error -> {
                    ErrorComponent(
                        message = refreshState.error.message ?: "Unknown error",
                        onRetry = { movies.retry() }
                    )
                }
                is LoadState.NotLoading -> {
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
                                MovieItem(movie = movie)
                            }
                        }

                        if (movies.loadState.append is LoadState.Loading) {
                            item {
                                LoadingComponent()
                            }
                        }
                    }
                }
            }
        }
    }
}
