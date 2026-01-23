package com.good.movies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.good.movies.domain.model.FavoriteMovie

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val voteAverage: Double,
    val releaseDate: String,
    val addedAt: Long = System.currentTimeMillis()
)

fun FavoriteMovieEntity.toDomain(): FavoriteMovie {
    return FavoriteMovie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}

fun FavoriteMovie.toEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}
