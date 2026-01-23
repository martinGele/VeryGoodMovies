package com.good.movies.domain.usecase

import com.good.movies.domain.model.FavoriteTvSeries
import com.good.movies.domain.repository.FavoriteTvSeriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteTvSeriesUseCase @Inject constructor(
    private val repository: FavoriteTvSeriesRepository
) {
    operator fun invoke(): Flow<List<FavoriteTvSeries>> {
        return repository.getAllFavorites()
    }
}
