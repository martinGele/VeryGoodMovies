package com.good.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.data.remote.dto.toDomain
import com.good.movies.domain.model.TvSeries

class SearchTvPagingSource(
    private val movieApiService: MovieApiService,
    private val query: String
) : PagingSource<Int, TvSeries>() {

    override fun getRefreshKey(state: PagingState<Int, TvSeries>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeries> {
        val page = params.key ?: 1
        return try {
            val response = movieApiService.searchTvSeries(query = query, page = page)
            val tvSeries = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = tvSeries,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
