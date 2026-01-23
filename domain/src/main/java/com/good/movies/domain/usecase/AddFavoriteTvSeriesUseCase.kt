package com.good.movies.domain.usecase

import com.good.movies.domain.model.FavoriteTvSeries
import com.good.movies.domain.repository.FavoriteTvSeriesRepository
import javax.inject.Inject

class AddFavoriteTvSeriesUseCase @Inject constructor(
    private val repository: FavoriteTvSeriesRepository
) {
    suspend operator fun invoke(tvSeries: FavoriteTvSeries) {
        repository.addFavorite(tvSeries)
    }
}
