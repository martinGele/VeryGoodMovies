package com.good.movies.domain.usecase

import androidx.paging.PagingData
import com.good.movies.domain.model.TvSeries
import com.good.movies.domain.repository.TvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTvUseCase @Inject constructor(
    private val repository: TvRepository
) {
    operator fun invoke(query: String): Flow<PagingData<TvSeries>> {
        return repository.searchTvSeries(query)
    }
}
