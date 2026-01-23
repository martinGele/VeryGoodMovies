package usecase

import com.good.movies.domain.model.FavoriteMovie
import com.good.movies.domain.repository.FavoriteMovieRepository
import com.good.movies.domain.usecase.GetFavoriteMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test


class GetFavoriteMoviesUseCaseTest {

    private val repository = mockk<FavoriteMovieRepository>()
    private val useCase = GetFavoriteMoviesUseCase(repository)

    @Test
    fun `invoke should return flow of favorite movies from repository`() = runTest {
        val movies = listOf(
            FavoriteMovie(
                id = 1,
                title = "Test Movie",
                posterPath = "/test.jpg",
                overview = "Overview",
                releaseDate = "2024-01-01",
                voteAverage = 8.5
            )
        )
        every { repository.getAllFavorites() } returns flowOf(movies)

        val result = useCase().toList()
        assertEquals(listOf(movies), result)
        verify { repository.getAllFavorites() }
    }
}
