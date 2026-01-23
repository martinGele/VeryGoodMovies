package com.good.movies.domain.repository

import androidx.paging.PagingData
import com.good.movies.core.util.NetworkResult
import com.good.movies.domain.model.TvSeries
import com.good.movies.domain.model.TvSeriesDetails
import kotlinx.coroutines.flow.Flow

interface TvRepository {
    fun searchTvSeries(query: String): Flow<PagingData<TvSeries>>
    suspend fun getTvSeriesDetails(tvId: Int): NetworkResult<TvSeriesDetails>
}
