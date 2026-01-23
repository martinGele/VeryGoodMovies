package com.good.movies.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.good.movies.ui.components.LoadingComponent
import com.good.movies.ui.theme.Spacing

@Composable
fun FavoritesScreen(
    onMovieClick: (Int) -> Unit,
    onTvSeriesClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoritesContent(
        uiState = uiState,
        onMovieClick = onMovieClick,
        onTvSeriesClick = onTvSeriesClick,
        onIntent = viewModel::handleIntent,
        modifier = modifier
    )
}

@Composable
private fun FavoritesContent(
    uiState: FavoritesUiState,
    onMovieClick: (Int) -> Unit,
    onTvSeriesClick: (Int) -> Unit,
    onIntent: (FavoritesIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                LoadingComponent()
            }

            uiState.favorites.isEmpty() -> {
                EmptyFavoritesMessage()
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(Spacing.small)
                ) {
                    items(
                        items = uiState.favorites,
                        key = { item ->
                            when (item) {
                                is FavoriteItem.Movie -> "movie_${item.id}"
                                is FavoriteItem.TvSeries -> "tv_${item.id}"
                            }
                        }
                    ) { item ->
                        FavoriteItemCard(
                            item = item,
                            onClick = {
                                when (item) {
                                    is FavoriteItem.Movie -> onMovieClick(item.id)
                                    is FavoriteItem.TvSeries -> onTvSeriesClick(item.id)
                                }
                            },
                            onRemove = {
                                when (item) {
                                    is FavoriteItem.Movie -> onIntent(FavoritesIntent.RemoveFavoriteMovie(item.id))
                                    is FavoriteItem.TvSeries -> onIntent(FavoritesIntent.RemoveFavoriteTvSeries(item.id))
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyFavoritesMessage(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No favorites yet",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(Spacing.small))
            Text(
                text = "Movies and TV shows you save will appear here",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

