import com.good.movies.core.util.NetworkResult
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class NetworkResultTest {


    @Test
    fun testNetworkResultSuccess() {
        val result = NetworkResult.Success("Test Data")
        assertTrue(result is NetworkResult.Success)
        assertEquals("Test Data", (result as NetworkResult.Success).data)
    }

    @Test
    fun testNetworkResultError() {
        val errorMessage = "An error occurred"
        val errorCode = 404
        val throwable = Throwable("Test Throwable")

        val result = NetworkResult.Error(
            message = errorMessage,
            code = errorCode,
            throwable = throwable
        )

        assertTrue(result is NetworkResult.Error)
        val errorResult = result as NetworkResult.Error
        assertEquals(errorMessage, errorResult.message)
        assertEquals(errorCode, errorResult.code)
        assertEquals(throwable, errorResult.throwable)
    }
}