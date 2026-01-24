package com.good.movies.ui.movies.moviedetails

import androidx.lifecycle.SavedStateHandle
import com.good.movies.core.util.NetworkResult
import com.good.movies.domain.model.FavoriteMovie
import com.good.movies.domain.model.MovieDetails
import com.good.movies.domain.usecase.AddFavoriteMovieUseCase
import com.good.movies.domain.usecase.GetMovieDetailsUseCase
import com.good.movies.domain.usecase.IsFavoriteMovieUseCase
import com.good.movies.domain.usecase.RemoveFavoriteMovieUseCase
import com.good.movies.ui.moviedetails.DetailsIntent
import com.good.movies.ui.moviedetails.DetailsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var isFavoriteMovieUseCase: IsFavoriteMovieUseCase
    private lateinit var addFavoriteMovieUseCase: AddFavoriteMovieUseCase
    private lateinit var removeFavoriteMovieUseCase: RemoveFavoriteMovieUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: DetailsViewModel

    private val testMovieId = 123
    private val testMovieDetails = MovieDetails(
        id = testMovieId,
        title = "Test Movie",
        originalTitle = "Test Movie Original",
        overview = "A test movie overview",
        posterPath = "/poster.jpg",
        backdropPath = "/backdrop.jpg",
        voteAverage = 8.5,
        voteCount = 1000,
        releaseDate = "2024-01-01",
        runtime = 120,
        budget = 100000000,
        revenue = 500000000,
        status = "Released",
        tagline = "A great test movie",
        homepage = "https://testmovie.com",
        genres = emptyList(),
        productionCompanies = emptyList(),
        spokenLanguages = emptyList()
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getMovieDetailsUseCase = mockk()
        isFavoriteMovieUseCase = mockk()
        addFavoriteMovieUseCase = mockk(relaxed = true)
        removeFavoriteMovieUseCase = mockk(relaxed = true)
        savedStateHandle = SavedStateHandle(mapOf("movieId" to testMovieId))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() {
        viewModel = DetailsViewModel(
            getMovieDetailsUseCase,
            isFavoriteMovieUseCase,
            addFavoriteMovieUseCase,
            removeFavoriteMovieUseCase,
            savedStateHandle
        )
    }

    @Test
    fun `LoadDetails intent should load movie details successfully`() = runTest {
        coEvery { getMovieDetailsUseCase(testMovieId) } returns NetworkResult.Success(testMovieDetails)
        every { isFavoriteMovieUseCase(testMovieId) } returns flowOf(false)

        createViewModel()
        advanceUntilIdle()

        viewModel.handleIntent(DetailsIntent.LoadDetails)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(testMovieDetails, uiState.movieDetails)
        assertNull(uiState.error)
    }

    @Test
    fun `LoadDetails intent should handle error`() = runTest {
        val errorMessage = "Network error"
        coEvery { getMovieDetailsUseCase(testMovieId) } returns NetworkResult.Error(errorMessage)
        every { isFavoriteMovieUseCase(testMovieId) } returns flowOf(false)

        createViewModel()
        advanceUntilIdle()

        viewModel.handleIntent(DetailsIntent.LoadDetails)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(errorMessage, uiState.error)
        assertNull(uiState.movieDetails)
    }

    @Test
    fun `ToggleFavorite intent should add movie to favorites when not favorite`() = runTest {
        coEvery { getMovieDetailsUseCase(testMovieId) } returns NetworkResult.Success(testMovieDetails)
        every { isFavoriteMovieUseCase(testMovieId) } returns flowOf(false)

        createViewModel()
        advanceUntilIdle()

        viewModel.handleIntent(DetailsIntent.ToggleFavorite)
        advanceUntilIdle()

        coVerify {
            addFavoriteMovieUseCase(
                FavoriteMovie(
                    id = testMovieDetails.id,
                    title = testMovieDetails.title,
                    overview = testMovieDetails.overview,
                    posterPath = testMovieDetails.posterPath,
                    voteAverage = testMovieDetails.voteAverage,
                    releaseDate = testMovieDetails.releaseDate
                )
            )
        }
    }

    @Test
    fun `ToggleFavorite intent should remove movie from favorites when favorite`() = runTest {
        coEvery { getMovieDetailsUseCase(testMovieId) } returns NetworkResult.Success(testMovieDetails)
        every { isFavoriteMovieUseCase(testMovieId) } returns flowOf(true)

        createViewModel()
        advanceUntilIdle()

        viewModel.handleIntent(DetailsIntent.ToggleFavorite)
        advanceUntilIdle()

        coVerify { removeFavoriteMovieUseCase(testMovieId) }
    }
}
