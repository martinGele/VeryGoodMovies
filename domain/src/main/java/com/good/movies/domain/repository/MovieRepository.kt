package com.good.movies.domain.repository

import androidx.paging.PagingData
import com.good.movies.domain.model.Movie
import com.good.movies.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    fun searchMovies(query: String): Flow<PagingData<Movie>>

    suspend fun getMovieDetails(movieId: Int): MovieDetails
}
