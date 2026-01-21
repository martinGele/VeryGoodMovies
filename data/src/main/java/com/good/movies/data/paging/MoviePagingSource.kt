package com.good.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.data.remote.dto.toDomain
import com.good.movies.domain.model.Movie

class MoviePagingSource(
    private val movieApiService: MovieApiService,
    private val query: String? = null
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        TODO("Not yet implemented")
    }


}
