package com.good.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.data.remote.dto.toDomain
import com.good.movies.domain.model.Movie

/**
 * PagingSource implementation to load top-rated movies from the MovieApiService.
 * also we map the DTO to domain model using the toDomain extension function.
 * and handle pagination by providing previous and next keys.
 */
class TopRatedMoviesPagingSource(
    private val movieApiService: MovieApiService
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = movieApiService.getTopRatedMovies(page = page)
            val movies = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
