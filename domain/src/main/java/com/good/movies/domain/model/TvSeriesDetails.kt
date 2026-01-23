package com.good.movies.domain.model

import java.util.Locale

data class TvSeriesDetails(
    val id: Int,
    val name: String,
    val originalName: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val firstAirDate: String,
    val lastAirDate: String,
    val status: String,
    val tagline: String,
    val homepage: String?,
    val numberOfSeasons: Int,
    val numberOfEpisodes: Int,
    val episodeRunTime: List<Int>,
    val genres: List<Genre>,
    val productionCompanies: List<ProductionCompany>,
    val networks: List<Network>,
    val createdBy: List<Creator>
) {
    val voteAverageFormatted: String
        get() = String.format(Locale.US, "%.1f", voteAverage)

    val episodeRuntimeFormatted: String
        get() = episodeRunTime.firstOrNull()?.let { "${it}m" } ?: ""

    val seasonsAndEpisodesFormatted: String
        get() = "$numberOfSeasons seasons, $numberOfEpisodes episodes"
}

data class Network(
    val id: Int,
    val name: String,
    val logoPath: String?
)

data class Creator(
    val id: Int,
    val name: String,
    val profilePath: String?
)
