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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.good.movies.ui.movies.components.ErrorContent
import com.good.movies.ui.movies.components.LoadingContent
import com.good.movies.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val topMovies = uiState.topRatedMovies.collectAsLazyPagingItems()

    /**
     * Pull to refresh implementation for the top movies list
     */
    PullToRefreshBox(
        isRefreshing = topMovies.loadState.refresh is LoadState.Loading,
        onRefresh = { topMovies.refresh() },
        modifier = modifier.fillMaxSize()
    ) {
        /**
         * Handling different load states: Loading, Error, and NotLoading
         */
        when (val refreshState = topMovies.loadState.refresh) {
            is LoadState.Loading -> {
                LoadingContent()
            }
            is LoadState.Error -> {
                ErrorContent(
                    message = refreshState.error.message ?: "Unknown error",
                    onRetry = { topMovies.retry() }
                )
            }
            /**
             * Displaying the list of top movies when data is successfully loaded
             */
            is LoadState.NotLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(Spacing.small)
                ) {
                    items(
                        count = topMovies.itemCount,
                        key = topMovies.itemKey { it.id }
                    ) { index ->
                        topMovies[index]?.let { movie ->
                            MovieContent(movie = movie)
                        }
                    }

                    /**
                     * Displaying a loading indicator at the end of the list when more data is being loaded
                     */
                    if (topMovies.loadState.append is LoadState.Loading) {
                        item {
                            LoadingContent()
                        }
                    }
                }
            }
        }
    }
}
