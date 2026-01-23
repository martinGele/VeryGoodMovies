package com.good.movies.ui.favorites

import androidx.compose.runtime.Stable
import com.good.movies.domain.model.FavoriteMovie

@Stable
data class FavoritesMoviesUiState(
    val favorites: List<FavoriteMovie> = emptyList(),
    val isLoading: Boolean = true
)


sealed interface FavoritesMoviesIntent {
    data object LoadFavorites : FavoritesMoviesIntent
    data class RemoveFavorite(val movieId: Int) : FavoritesMoviesIntent
}
