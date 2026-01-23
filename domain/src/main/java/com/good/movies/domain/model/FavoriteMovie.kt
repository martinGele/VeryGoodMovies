package com.good.movies.domain.model

import java.util.Locale

data class FavoriteMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val voteAverage: Double,
    val releaseDate: String
) {
    val voteAverageFormatted: String
        get() = String.format(Locale.US, "%.1f", voteAverage)
}
