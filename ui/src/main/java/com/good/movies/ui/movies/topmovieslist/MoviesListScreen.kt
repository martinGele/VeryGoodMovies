package com.good.movies.ui.movies.topmovieslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.good.movies.domain.model.Movie
import com.good.movies.ui.components.ErrorComponent
import com.good.movies.ui.components.LoadingComponent
import com.good.movies.ui.theme.Spacing

@Composable
fun MoviesListScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val movies = uiState.movies.collectAsLazyPagingItems()

    MoviesScreenContent(
        movies = movies,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesScreenContent(
    movies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = movies.loadState.refresh is LoadState.Loading,
        onRefresh = { movies.refresh() },
        modifier = modifier.fillMaxSize()
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
