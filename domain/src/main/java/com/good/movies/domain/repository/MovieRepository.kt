package com.good.movies.domain.repository

import androidx.paging.PagingData
import com.good.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTopRatedMovies(): Flow<PagingData<Movie>>
}
