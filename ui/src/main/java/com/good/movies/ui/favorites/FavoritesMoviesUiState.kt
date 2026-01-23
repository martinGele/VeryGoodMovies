package com.good.movies.ui.favorites

import androidx.compose.runtime.Stable
import com.good.movies.domain.model.FavoriteMovie
import com.good.movies.domain.model.FavoriteTvSeries

@Stable
data class FavoritesUiState(
    val favorites: List<FavoriteItem> = emptyList(),
    val isLoading: Boolean = true
)

sealed class FavoriteItem(
    open val id: Int,
    open val title: String,
    open val overview: String,
    open val posterPath: String?,
    open val voteAverageFormatted: String,
    open val date: String
) {
    data class Movie(
        override val id: Int,
        override val title: String,
        override val overview: String,
        override val posterPath: String?,
        override val voteAverageFormatted: String,
        override val date: String
    ) : FavoriteItem(id, title, overview, posterPath, voteAverageFormatted, date)

    data class TvSeries(
        override val id: Int,
        override val title: String,
        override val overview: String,
        override val posterPath: String?,
        override val voteAverageFormatted: String,
        override val date: String
    ) : FavoriteItem(id, title, overview, posterPath, voteAverageFormatted, date)
}

fun FavoriteMovie.toFavoriteItem(): FavoriteItem.Movie {
    return FavoriteItem.Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        voteAverageFormatted = voteAverageFormatted,
        date = releaseDate
    )
}

fun FavoriteTvSeries.toFavoriteItem(): FavoriteItem.TvSeries {
    return FavoriteItem.TvSeries(
        id = id,
        title = name,
        overview = overview,
        posterPath = posterPath,
        voteAverageFormatted = voteAverageFormatted,
        date = firstAirDate
    )
}

sealed interface FavoritesIntent {
    data object LoadFavorites : FavoritesIntent
    data class RemoveFavoriteMovie(val movieId: Int) : FavoritesIntent
    data class RemoveFavoriteTvSeries(val tvId: Int) : FavoritesIntent
}
