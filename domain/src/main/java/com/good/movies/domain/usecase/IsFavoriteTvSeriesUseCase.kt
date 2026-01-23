package com.good.movies.domain.usecase

import com.good.movies.domain.repository.FavoriteTvSeriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteTvSeriesUseCase @Inject constructor(
    private val repository: FavoriteTvSeriesRepository
) {
    operator fun invoke(tvId: Int): Flow<Boolean> {
        return repository.isFavorite(tvId)
    }
}
