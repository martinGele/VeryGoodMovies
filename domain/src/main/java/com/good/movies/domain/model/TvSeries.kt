package com.good.movies.domain.model

import java.util.Locale

data class TvSeries(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val firstAirDate: String,
    val originalLanguage: String
) {

    val voteAverageAndCount: String
        get() = "⭐ ${String.format(Locale.US, "%.1f", voteAverage)} ($voteCount)"
}
