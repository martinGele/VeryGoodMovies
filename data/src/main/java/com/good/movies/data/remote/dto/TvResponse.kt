package com.good.movies.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object representing a response containing a list of TV series.
 */
data class TvResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<TvDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
