package com.good.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.good.movies.core.util.NetworkResult
import com.good.movies.core.util.safeApiCall
import com.good.movies.data.paging.SearchTvPagingSource
import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.data.remote.dto.toDomain
import com.good.movies.domain.model.TvSeries
import com.good.movies.domain.model.TvSeriesDetails
import com.good.movies.domain.repository.TvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : TvRepository {

    override fun searchTvSeries(query: String): Flow<PagingData<TvSeries>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { SearchTvPagingSource(movieApiService, query) }
        ).flow
    }

    override suspend fun getTvSeriesDetails(tvId: Int): NetworkResult<TvSeriesDetails> {
        return when (val result = safeApiCall { movieApiService.getTvSeriesDetails(tvId) }) {
            is NetworkResult.Success -> NetworkResult.Success(result.data.toDomain())
            is NetworkResult.Error -> result
        }
    }
}
