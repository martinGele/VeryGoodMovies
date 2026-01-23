package usecase

import com.good.movies.domain.repository.FavoriteMovieRepository
import com.good.movies.domain.usecase.RemoveFavoriteMovieUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RemoveFavoriteMovieUseCaseTest {

    private val repository = mockk<FavoriteMovieRepository>()
    private val useCase = RemoveFavoriteMovieUseCase(repository)

    @Test
    fun `invoke should call repository removeFavorite with correct movieId`() = runTest {
        val movieId = 123
        coJustRun { repository.removeFavorite(movieId) }

        useCase(movieId)

        coVerify { repository.removeFavorite(movieId) }
    }

    @Test
    fun `invoke should call repository removeFavorite for different movieIds`() = runTest {
        val movieId1 = 100
        val movieId2 = 200
        coJustRun { repository.removeFavorite(any()) }

        useCase(movieId1)
        useCase(movieId2)

        coVerify { repository.removeFavorite(movieId1) }
        coVerify { repository.removeFavorite(movieId2) }
    }
}
