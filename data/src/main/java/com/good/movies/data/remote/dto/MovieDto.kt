package com.good.movies.data.remote.dto

import com.good.movies.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
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
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("original_language")
    val originalLanguage: String
)

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath?.let { "$IMAGE_BASE_URL$it" },
        backdropPath = backdropPath?.let { "$IMAGE_BASE_URL$it" },
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseDate = releaseDate,
        originalLanguage = originalLanguage
    )
}
