package usecase

import com.good.movies.domain.repository.FavoriteMovieRepository
import com.good.movies.domain.usecase.IsFavoriteMovieUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class IsFavoriteMovieUseCaseTest {

    private val repository = mockk<FavoriteMovieRepository>()
    private val useCase = IsFavoriteMovieUseCase(repository)

    @Test
    fun `invoke should return true when movie is favorite`() = runTest {
        val movieId = 123
        every { repository.isFavorite(movieId) } returns flowOf(true)

        val result = useCase(movieId).first()

        assertEquals(true, result)
        verify { repository.isFavorite(movieId) }
    }

    @Test
    fun `invoke should return false when movie is not favorite`() = runTest {
        val movieId = 456
        every { repository.isFavorite(movieId) } returns flowOf(false)

        val result = useCase(movieId).first()

        assertEquals(false, result)
        verify { repository.isFavorite(movieId) }
    }
}
