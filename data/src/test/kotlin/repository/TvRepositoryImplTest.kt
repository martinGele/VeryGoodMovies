package repository

import com.good.movies.data.remote.api.MovieApiService
import com.good.movies.data.repository.TvRepositoryImpl
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class TvRepositoryImplTest {

    private val movieApiService: MovieApiService = mockk()
    private lateinit var repository: TvRepositoryImpl

    @Before
    fun setup() {
        repository = TvRepositoryImpl(movieApiService)
    }

    @Test
    fun `searchTvSeries returns non-null Flow`() = runTest {
        // GIVEN
        val query = "Breaking Bad"

        // WHEN
        val result = repository.searchTvSeries(query)

        // THEN
        assertNotNull(result)
    }
}
