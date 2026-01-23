package com.good.movies.domain.model

import java.util.Locale

data class FavoriteTvSeries(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val voteAverage: Double,
    val firstAirDate: String
) {
    val voteAverageFormatted: String
        get() = String.format(Locale.US, "%.1f", voteAverage)
}
