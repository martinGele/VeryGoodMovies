package com.good.movies.data.remote.dto

import com.good.movies.domain.model.Genre
import com.good.movies.domain.model.MovieDetails
import com.good.movies.domain.model.ProductionCompany
import com.good.movies.domain.model.SpokenLanguage
import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("original_title")
    val originalTitle: String,
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
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("budget")
    val budget: Long,
    @SerializedName("revenue")
    val revenue: Long,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto>
)

data class GenreDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

data class ProductionCompanyDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("origin_country")
    val originCountry: String
)

data class SpokenLanguageDto(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("name")
    val name: String
)

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath?.let { "$IMAGE_BASE_URL$it" },
        backdropPath = backdropPath?.let { "$IMAGE_BASE_URL$it" },
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseDate = releaseDate,
        runtime = runtime,
        budget = budget,
        revenue = revenue,
        status = status,
        tagline = tagline,
        homepage = homepage,
        genres = genres.map { it.toDomain() },
        productionCompanies = productionCompanies.map { it.toDomain() },
        spokenLanguages = spokenLanguages.map { it.toDomain() }
    )
}

fun GenreDto.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun ProductionCompanyDto.toDomain(): ProductionCompany {
    return ProductionCompany(
        id = id,
        name = name,
        logoPath = logoPath?.let { "$IMAGE_BASE_URL$it" },
        originCountry = originCountry
    )
}

fun SpokenLanguageDto.toDomain(): SpokenLanguage {
    return SpokenLanguage(
        englishName = englishName,
        name = name
    )
}