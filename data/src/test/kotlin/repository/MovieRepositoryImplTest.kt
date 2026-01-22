package repository

import com.good.movies.core.util.NetworkResult
import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.data.remote.dto.GenreDto
import com.good.movies.data.remote.dto.MovieDetailsDto
import com.good.movies.data.remote.dto.ProductionCompanyDto
import com.good.movies.data.remote.dto.SpokenLanguageDto
import com.good.movies.data.remote.dto.toDomain
import com.good.movies.data.repository.MovieRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MovieRepositoryImplTest {

    private val movieApiService: MovieApiService = mockk()
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        repository = MovieRepositoryImpl(movieApiService)
    }


    @Test
    fun `getMovieDetails returns Success when api call succeeds`() = runTest {
        // GIVEN
        val movieId = 550
        val movieDetailsDto = createMovieDetailsDto(movieId)

        coEvery {
            movieApiService.getMovieDetails(movieId)
        } returns movieDetailsDto

        // WHEN
        val result = repository.getMovieDetails(movieId)

        // THEN
        assertTrue(result is NetworkResult.Success)
        val successResult = result as NetworkResult.Success
        assertEquals(movieDetailsDto.toDomain(), successResult.data)
        coVerify(exactly = 1) { movieApiService.getMovieDetails(movieId) }
    }

    @Test
    fun `getMovieDetails returns Error when api throws IOException`() = runTest {
        // GIVEN
        val movieId = 550

        coEvery {
            movieApiService.getMovieDetails(movieId)
        } throws IOException("No internet connection")

        // WHEN
        val result = repository.getMovieDetails(movieId)

        // THEN
        assertTrue(result is NetworkResult.Error)
        val errorResult = result as NetworkResult.Error
        assertEquals("Network error. Please check your connection.", errorResult.message)
        assertTrue(errorResult.throwable is IOException)
    }

    @Test
    fun `getMovieDetails returns Error when api throws HttpException`() = runTest {
        // GIVEN
        val movieId = 999999
        val httpException = HttpException(
            Response.error<Any>(404, "Not found".toResponseBody(null))
        )

        coEvery {
            movieApiService.getMovieDetails(movieId)
        } throws httpException

        // WHEN
        val result = repository.getMovieDetails(movieId)

        // THEN
        assertTrue(result is NetworkResult.Error)
        val errorResult = result as NetworkResult.Error
        assertEquals(404, errorResult.code)
        assertTrue(errorResult.throwable is HttpException)
    }

    @Test
    fun `getMovieDetails returns Error when api throws generic Exception`() = runTest {
        // GIVEN
        val movieId = 550
        val exception = RuntimeException("Something went wrong")

        coEvery {
            movieApiService.getMovieDetails(movieId)
        } throws exception

        // WHEN
        val result = repository.getMovieDetails(movieId)

        // THEN
        assertTrue(result is NetworkResult.Error)
        val errorResult = result as NetworkResult.Error
        assertEquals("Something went wrong", errorResult.message)
    }

    @Test
    fun `getMovieDetails maps dto to domain correctly`() = runTest {
        // GIVEN
        val movieId = 550
        val movieDetailsDto = createMovieDetailsDto(movieId)

        coEvery {
            movieApiService.getMovieDetails(movieId)
        } returns movieDetailsDto

        // WHEN
        val result = repository.getMovieDetails(movieId)

        // THEN
        assertTrue(result is NetworkResult.Success)
        val movieDetails = (result as NetworkResult.Success).data

        assertEquals(movieId, movieDetails.id)
        assertEquals("Fight Club", movieDetails.title)
        assertEquals("Fight Club", movieDetails.originalTitle)
        assertEquals("A ticking-time-bomb insomniac...", movieDetails.overview)
    }

    private fun createMovieDetailsDto(movieId: Int) = MovieDetailsDto(
        id = movieId,
        title = "Fight Club",
        originalTitle = "Fight Club",
        overview = "A ticking-time-bomb insomniac...",
        posterPath = "/poster.jpg",
        backdropPath = "/backdrop.jpg",
        voteAverage = 8.4,
        voteCount = 26000,
        releaseDate = "1999-10-15",
        runtime = 139,
        budget = 63000000L,
        revenue = 100853753L,
        status = "Released",
        tagline = "Mischief. Mayhem. Soap.",
        homepage = "",
        genres = listOf(
            GenreDto(id = 18, name = "Drama"),
            GenreDto(id = 53, name = "Thriller")
        ),
        productionCompanies = listOf(
            ProductionCompanyDto(
                id = 25,
                name = "20th Century Fox",
                logoPath = "",
                originCountry = "US"
            )
        ),
        spokenLanguages = listOf(
            SpokenLanguageDto(
                englishName = "English",
                name = "English"
            )
        )
    )
}
