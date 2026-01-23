package com.good.movies.data.remote.dto

import com.good.movies.domain.model.Creator
import com.good.movies.domain.model.Network
import com.good.movies.domain.model.TvSeriesDetails
import com.google.gson.annotations.SerializedName

data class TvSeriesDetailsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("last_air_date")
    val lastAirDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,
    @SerializedName("networks")
    val networks: List<NetworkDto>,
    @SerializedName("created_by")
    val createdBy: List<CreatorDto>
)

data class NetworkDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("logo_path")
    val logoPath: String?
)

data class CreatorDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?
)

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun TvSeriesDetailsDto.toDomain(): TvSeriesDetails {
    return TvSeriesDetails(
        id = id,
        name = name,
        originalName = originalName,
        overview = overview,
        posterPath = posterPath?.let { "$IMAGE_BASE_URL$it" },
        backdropPath = backdropPath?.let { "$IMAGE_BASE_URL$it" },
        voteAverage = voteAverage,
        voteCount = voteCount,
        firstAirDate = firstAirDate,
        lastAirDate = lastAirDate,
        status = status,
        tagline = tagline,
        homepage = homepage,
        numberOfSeasons = numberOfSeasons,
        numberOfEpisodes = numberOfEpisodes,
        episodeRunTime = episodeRunTime,
        genres = genres.map { it.toDomain() },
        productionCompanies = productionCompanies.map { it.toDomain() },
        networks = networks.map { it.toDomain() },
        createdBy = createdBy.map { it.toDomain() }
    )
}

fun NetworkDto.toDomain(): Network {
    return Network(
        id = id,
        name = name,
        logoPath = logoPath?.let { "$IMAGE_BASE_URL$it" }
    )
}

fun CreatorDto.toDomain(): Creator {
    return Creator(
        id = id,
        name = name,
        profilePath = profilePath?.let { "$IMAGE_BASE_URL$it" }
    )
}
