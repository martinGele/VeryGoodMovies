package usecase

import com.good.movies.core.util.NetworkResult
import com.good.movies.domain.model.Genre
import com.good.movies.domain.model.MovieDetails
import com.good.movies.domain.model.ProductionCompany
import com.good.movies.domain.model.SpokenLanguage
import com.good.movies.domain.repository.MovieRepository
import com.good.movies.domain.usecase.GetMovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetMovieDetailsUseCaseTest {

    val repository  = mockk<MovieRepository>()
    val useCase = GetMovieDetailsUseCase(repository)



    @Test
    fun `invoke should return movie details from repository`() = runTest {
        val movieId = 1
        val details = MovieDetails(
            id = 1,
            title = "Test Movie",
            originalTitle = "Test Movie Original",
            overview = "A test overview.",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            voteCount = 100,
            releaseDate = "2024-01-01",
            runtime = 120,
            budget = 100000000,
            revenue = 500000000,
            status = "Released",
            tagline = "An epic test movie",
            homepage = "https://testmovie.com",
            genres = listOf(Genre(id = 1, name = "Action"), Genre(id = 2, name = "Adventure")),
            productionCompanies = listOf(
                ProductionCompany(
                    id = 1,
                    name = "Test Productions",
                    logoPath = "/logo.png",
                    originCountry = "USA"
                )
            ),
            spokenLanguages = listOf(
                SpokenLanguage(englishName = "English", name = "English"),
                SpokenLanguage(englishName = "Spanish", name = "Español")
            )
        )

        val expectedResult = NetworkResult.Success(details)
        coEvery { repository.getMovieDetails(movieId) } returns expectedResult

        val result = useCase.invoke(movieId)

        assertEquals(expectedResult, result)
        coVerify { repository.getMovieDetails(movieId) }
    }
}