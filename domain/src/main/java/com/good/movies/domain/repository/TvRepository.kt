package com.good.movies.domain.repository

import androidx.paging.PagingData
import com.good.movies.domain.model.TvSeries
import kotlinx.coroutines.flow.Flow

interface TvRepository {
    fun searchTvSeries(query: String): Flow<PagingData<TvSeries>>
}
