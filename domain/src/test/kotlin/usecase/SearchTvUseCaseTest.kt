package usecase

import androidx.paging.PagingData
import com.good.movies.domain.model.TvSeries
import com.good.movies.domain.repository.TvRepository
import com.good.movies.domain.usecase.SearchTvUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Test

class SearchTvUseCaseTest {

    private val repository = mockk<TvRepository>()
    private val useCase = SearchTvUseCase(repository)

    @Test
    fun `invoke should call repository searchTvSeries with correct query`() = runTest {
        val query = "Breaking Bad"
        val pagingData = PagingData.from(listOf(createTestTvSeries()))
        every { repository.searchTvSeries(query) } returns flowOf(pagingData)

        val result = useCase(query).first()

        assertNotNull(result)
        verify { repository.searchTvSeries(query) }
    }

    @Test
    fun `invoke should return flow of PagingData from repository`() = runTest {
        val query = "Game"
        val tvSeriesList = listOf(
            createTestTvSeries(id = 1, name = "Game of Thrones"),
            createTestTvSeries(id = 2, name = "Squid Game")
        )
        val pagingData = PagingData.from(tvSeriesList)
        every { repository.searchTvSeries(query) } returns flowOf(pagingData)

        val result = useCase(query).first()

        assertNotNull(result)
        verify { repository.searchTvSeries(query) }
    }

    @Test
    fun `invoke should handle empty query`() = runTest {
        val query = ""
        val pagingData = PagingData.empty<TvSeries>()
        every { repository.searchTvSeries(query) } returns flowOf(pagingData)

        val result = useCase(query).first()

        assertNotNull(result)
        verify { repository.searchTvSeries(query) }
    }

    private fun createTestTvSeries(
        id: Int = 1,
        name: String = "Test TV Series"
    ) = TvSeries(
        id = id,
        name = name,
        overview = "Test overview",
        posterPath = "/test.jpg",
        backdropPath = "/backdrop.jpg",
        voteAverage = 9.0,
        voteCount = 5000,
        firstAirDate = "2020-01-01",
        originalLanguage = "en"
    )
}
