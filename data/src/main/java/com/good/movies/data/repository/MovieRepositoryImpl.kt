package com.good.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.good.movies.data.paging.SearchMoviesPagingSource
import com.good.movies.data.paging.TopRatedMoviesPagingSource
import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.domain.model.Movie
import com.good.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
) : MovieRepository {

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { TopRatedMoviesPagingSource(movieApiService) }
        ).flow
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { SearchMoviesPagingSource(movieApiService, query) }
        ).flow
    }
}
