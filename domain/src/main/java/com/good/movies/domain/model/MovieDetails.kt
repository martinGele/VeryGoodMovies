package com.good.movies.domain.model

import java.util.Locale

data class MovieDetails(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val runtime: Int?,
    val budget: Long,
    val revenue: Long,
    val status: String,
    val tagline: String,
    val homepage: String?,
    val genres: List<Genre>,
    val productionCompanies: List<ProductionCompany>,
    val spokenLanguages: List<SpokenLanguage>
) {
    val voteAverageFormatted: String
        get() = String.format(Locale.US, "%.1f", voteAverage)

    val runtimeFormatted: String
        get() = runtime?.let {
            val hours = it / 60
            val minutes = it % 60
            if (hours > 0) "${hours}h ${minutes}m" else "${minutes}m"
        } ?: ""

    val budgetFormatted: String
        get() = if (budget > 0) "$${String.format(Locale.US, "%,d", budget)}" else ""

    val revenueFormatted: String
        get() = if (revenue > 0) "$${String.format(Locale.US, "%,d", revenue)}" else ""

    val genresText: String
        get() = genres.joinToString(", ") { it.name }
}

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val name: String,
    val logoPath: String?,
    val originCountry: String
)

data class SpokenLanguage(
    val englishName: String,
    val name: String
)