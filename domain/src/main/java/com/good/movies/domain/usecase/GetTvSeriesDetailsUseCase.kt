package com.good.movies.domain.usecase

import com.good.movies.core.util.NetworkResult
import com.good.movies.domain.model.TvSeriesDetails
import com.good.movies.domain.repository.TvRepository
import javax.inject.Inject

class GetTvSeriesDetailsUseCase @Inject constructor(
    private val repository: TvRepository
) {
    suspend operator fun invoke(tvId: Int): NetworkResult<TvSeriesDetails> {
        return repository.getTvSeriesDetails(tvId)
    }
}
