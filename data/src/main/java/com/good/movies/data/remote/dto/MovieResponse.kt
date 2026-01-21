package com.good.movies.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object representing a response containing a list of movies.
 */
data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
