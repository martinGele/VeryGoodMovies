package usecase

import com.good.movies.domain.model.FavoriteMovie
import com.good.movies.domain.repository.FavoriteMovieRepository
import com.good.movies.domain.usecase.AddFavoriteMovieUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AddFavoriteMoviesUseCaseTest {


    private val repository = mockk<FavoriteMovieRepository>()
    private val useCase = AddFavoriteMovieUseCase(repository)

    @Test
    fun `test add favorite movie use case`() = runTest {
        val movie = FavoriteMovie(
            id = 1,
            title = "Test Movie",
            posterPath = "/test.jpg",
            overview = "Overview",
            releaseDate = "2024-01-01",
            voteAverage = 8.5
        )
        coJustRun { repository.addFavorite(any()) }
        useCase(movie)
        coVerify { repository.addFavorite(movie) }
    }
}