package paging

import com.good.movies.data.paging.SearchTvPagingSource
import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.data.remote.dto.TvDto
import com.good.movies.data.remote.dto.TvResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchTvPagingSourceTest {

    private val movieApiService: MovieApiService = mockk()
    private lateinit var pagingSource: SearchTvPagingSource

    private val query = "Martin"

    @Before
    fun setup() {
        pagingSource = SearchTvPagingSource(
            movieApiService = movieApiService,
            query = query
        )
    }

    @Test
    fun `load returns Page on successful first page`() = runTest {

        // GIVEN
        val tvDtos = listOf(
            TvDto(
                id = 1,
                name = "Martin Show",
                overview = "A hero in Gotham",
                posterPath = "",
                backdropPath = "",
                voteAverage = 8.5,
                voteCount = 1000,
                firstAirDate = "",
                originalLanguage = "en"
            ),
            TvDto(
                id = 2,
                name = "Dark Knight Series",
                overview = "The Dark Knight rises",
                posterPath = "",
                backdropPath = "",
                voteAverage = 9.0,
                voteCount = 2500,
                firstAirDate = "",
                originalLanguage = "en"
            )
        )

        coEvery {
            movieApiService.searchTvSeries(query = query, page = 1)
        } returns TvResponse(
            page = 1,
            results = tvDtos,
            totalPages = 2,
            totalResults = 4
        )

        // WHEN
        val result = pagingSource.load(
            params = androidx.paging.PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        // THEN
        assert(result is androidx.paging.PagingSource.LoadResult.Page)
        val page = result as androidx.paging.PagingSource.LoadResult.Page
        assert(page.data.size == 2)
        assert(page.prevKey == null)
        assert(page.nextKey == 2)
    }


    @Test
    fun `load returns Error when api throws`() = runTest {
        // GIVEN
        val exception = Exception("Network error")

        coEvery {
            movieApiService.searchTvSeries(query = query, page = 1)
        } throws exception

        // WHEN
        val result = pagingSource.load(
            params = androidx.paging.PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        // THEN
        assert(result is androidx.paging.PagingSource.LoadResult.Error)
        val errorResult = result as androidx.paging.PagingSource.LoadResult.Error
        assert(errorResult.throwable == exception)
    }

}