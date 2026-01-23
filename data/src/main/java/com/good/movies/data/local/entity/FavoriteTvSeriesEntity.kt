package com.good.movies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.good.movies.domain.model.FavoriteTvSeries

@Entity(tableName = "favorite_tv_series")
data class FavoriteTvSeriesEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val voteAverage: Double,
    val firstAirDate: String,
    val addedAt: Long = System.currentTimeMillis()
)

fun FavoriteTvSeriesEntity.toDomain(): FavoriteTvSeries {
    return FavoriteTvSeries(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage,
        firstAirDate = firstAirDate
    )
}

fun FavoriteTvSeries.toEntity(): FavoriteTvSeriesEntity {
    return FavoriteTvSeriesEntity(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage,
        firstAirDate = firstAirDate
    )
}
