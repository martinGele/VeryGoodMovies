package com.good.movies.data.remote.dto

import com.good.movies.domain.model.TvSeries
import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object representing a TV series item.
 */
data class TvDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
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
    val firstAirDate: String?,
    @SerializedName("original_language")
    val originalLanguage: String
)

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun TvDto.toDomain(): TvSeries {
    return TvSeries(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath?.let { "$IMAGE_BASE_URL$it" },
        backdropPath = backdropPath?.let { "$IMAGE_BASE_URL$it" },
        voteAverage = voteAverage,
        voteCount = voteCount,
        firstAirDate = firstAirDate ?: "",
        originalLanguage = originalLanguage
    )
}
