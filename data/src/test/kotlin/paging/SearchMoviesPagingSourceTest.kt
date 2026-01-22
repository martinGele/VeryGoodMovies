package paging

import androidx.paging.PagingSource
import com.good.movies.data.paging.SearchMoviesPagingSource
import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.data.remote.dto.MovieDto
import com.good.movies.data.remote.dto.MovieResponse
import com.good.movies.data.remote.dto.toDomain
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class SearchMoviesPagingSourceTest {

    private val movieApiService: MovieApiService = mockk()
    private lateinit var pagingSource: SearchMoviesPagingSource

    private val query = "Martin"

    @Before
    fun setup() {
        pagingSource = SearchMoviesPagingSource(
            movieApiService = movieApiService,
            query = query
        )
    }


    @Test
    fun `load returns Page on successful first page`() = runTest {
        // GIVEN
        val movieDtos = listOf(
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
            movieApiService.searchMovies(query = query, page = 1)
        } returns MovieResponse(
            page = 1,
            results = movieDtos,
            totalPages = 2,
            totalResults = 4
        )

        // WHEN
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // THEN
        val expected = PagingSource.LoadResult.Page(
            data = movieDtos.map { it.toDomain() },
            prevKey = null,
            nextKey = 2
        )

        assertEquals(expected, result)
    }


    @Test
    fun `load returns Error when api throws`() = runTest {
        // GIVEN
        val exception = RuntimeException("Network error")

        coEvery {
            movieApiService.searchMovies(any(), any())
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
