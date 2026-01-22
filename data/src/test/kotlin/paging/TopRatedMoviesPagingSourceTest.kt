package paging

import androidx.paging.PagingSource
import com.good.movies.data.paging.TopRatedMoviesPagingSource
import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.data.remote.dto.MovieDto
import com.good.movies.data.remote.dto.MovieResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TopRatedMoviesPagingSourceTest {

    private val movieApiService: MovieApiService = mockk()
    private lateinit var pagingSource: TopRatedMoviesPagingSource

    @Before
    fun setup() {
        pagingSource = TopRatedMoviesPagingSource(
            movieApiService = movieApiService
        )
    }

    @Test
    fun `load returns Page on successful first page`() = runTest {
        // WHEN
        val movieDto = listOf(
            MovieDto(
                id = 1,
                title = "Martin",
                overview = "A hero in Gotham",
                posterPath = "",
                backdropPath = "",
                voteAverage = 8.5,
                voteCount = 1000,
                releaseDate = "",
                originalLanguage = "en"
            ),
            MovieDto(
                id = 2,
                title = "Dark Knight",
                overview = "The Dark Knight rises",
                posterPath = "",
                backdropPath = "",
                voteAverage = 9.0,
                voteCount = 2500,
                releaseDate = "",
                originalLanguage = "en"
            )
        )

        coEvery {
            movieApiService.getTopRatedMovies(page = 1)
        } returns MovieResponse(
            page = 1,
            results = movieDto,
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
        val exception = RuntimeException("Network error")

        coEvery {
            movieApiService.getTopRatedMovies(any())
        } throws exception

        // WHEN
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // THEN
        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals(exception, (result as PagingSource.LoadResult.Error).throwable)
    }
}