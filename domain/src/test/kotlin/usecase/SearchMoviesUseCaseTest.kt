package usecase

import androidx.paging.PagingData
import com.good.movies.domain.model.Movie
import com.good.movies.domain.repository.MovieRepository
import com.good.movies.domain.usecase.SearchMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Test

class SearchMoviesUseCaseTest {

    private val repository = mockk<MovieRepository>()
    private val useCase = SearchMoviesUseCase(repository)

    @Test
    fun `invoke should call repository searchMovies with correct query`() = runTest {
        val query = "Inception"
        val pagingData = PagingData.from(listOf(createTestMovie()))
        every { repository.searchMovies(query) } returns flowOf(pagingData)

        val result = useCase(query).first()

        assertNotNull(result)
        verify { repository.searchMovies(query) }
    }

    @Test
    fun `invoke should return flow of PagingData from repository`() = runTest {
        val query = "Matrix"
        val movies = listOf(
            createTestMovie(id = 1, title = "The Matrix"),
            createTestMovie(id = 2, title = "The Matrix Reloaded")
        )
        val pagingData = PagingData.from(movies)
        every { repository.searchMovies(query) } returns flowOf(pagingData)

        val result = useCase(query).first()

        assertNotNull(result)
        verify { repository.searchMovies(query) }
    }

    @Test
    fun `invoke should handle empty query`() = runTest {
        val query = ""
        val pagingData = PagingData.empty<Movie>()
        every { repository.searchMovies(query) } returns flowOf(pagingData)

        val result = useCase(query).first()

        assertNotNull(result)
        verify { repository.searchMovies(query) }
    }

    private fun createTestMovie(
        id: Int = 1,
        title: String = "Test Movie"
    ) = Movie(
        id = id,
        title = title,
        overview = "Test overview",
        posterPath = "/test.jpg",
        backdropPath = "/backdrop.jpg",
        voteAverage = 8.5,
        voteCount = 1000,
        releaseDate = "2024-01-01",
        originalLanguage = "en"
    )
}
