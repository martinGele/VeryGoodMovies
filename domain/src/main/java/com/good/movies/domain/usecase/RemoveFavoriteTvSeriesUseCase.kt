package com.good.movies.domain.usecase

import com.good.movies.domain.repository.FavoriteTvSeriesRepository
import javax.inject.Inject

class RemoveFavoriteTvSeriesUseCase @Inject constructor(
    private val repository: FavoriteTvSeriesRepository
) {
    suspend operator fun invoke(tvId: Int) {
        repository.removeFavorite(tvId)
    }
}
