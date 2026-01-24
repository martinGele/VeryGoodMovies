package com.good.movies.ui.movies.topmovieslist

import androidx.paging.PagingData
import com.good.movies.domain.model.Movie
import com.good.movies.domain.usecase.GetTopRatedMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
    private lateinit var viewModel: MoviesListViewModel

    private val testMovie = Movie(
        id = 1,
        title = "Test Movie",
        overview = "Test overview",
        posterPath = "/test.jpg",
        backdropPath = "/backdrop.jpg",
        voteAverage = 8.5,
        voteCount = 100,
        releaseDate = "2024-01-01",
        originalLanguage = "en"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getTopRatedMoviesUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should call use case and update uiState with movies`() = runTest {
        val pagingData = PagingData.from(listOf(testMovie))
        val moviesFlow = flowOf(pagingData)

        every { getTopRatedMoviesUseCase() } returns moviesFlow

        viewModel = MoviesListViewModel(getTopRatedMoviesUseCase)

        verify { getTopRatedMoviesUseCase() }
        assertNotNull(viewModel.uiState.value.movies)
    }

    @Test
    fun `uiState movies flow should emit paging data`() = runTest {
        val pagingData = PagingData.from(listOf(testMovie))
        val moviesFlow = flowOf(pagingData)

        every { getTopRatedMoviesUseCase() } returns moviesFlow

        viewModel = MoviesListViewModel(getTopRatedMoviesUseCase)

        val result = viewModel.uiState.value.movies.first()

        assertNotNull(result)
    }

    @Test
    fun `uiState should have movies flow after initialization`() = runTest {
        val emptyPagingData = PagingData.empty<Movie>()
        val moviesFlow = flowOf(emptyPagingData)

        every { getTopRatedMoviesUseCase() } returns moviesFlow

        viewModel = MoviesListViewModel(getTopRatedMoviesUseCase)

        val uiState = viewModel.uiState.value
        assertNotNull(uiState.movies)
    }
}
