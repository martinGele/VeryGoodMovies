package com.good.movies.data.remote.dto

import com.good.movies.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("title")
    val title: String
)

fun MovieDto.toDomain(): Movie {
    return Movie(
        title = this.title
    )
}
