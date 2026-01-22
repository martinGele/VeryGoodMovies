import com.good.movies.core.util.NetworkResult
import com.good.movies.core.util.safeApiCall
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class SafeApiCallTest {


    @Test
    fun `returns Success when api call succeeds`() = runTest{
        val result = safeApiCall {
            "Success"
        }

        assertTrue(result is NetworkResult.Success)
        assertTrue((result as NetworkResult.Success).data == "Success")
        assertEquals("Success", result.data)

    }


    @Test
    fun `returns Error on HttpException`() = runTest {
        val exception = HttpException(
            Response.error<String>(
                404,
                "Not found".toResponseBody("text/plain".toMediaType())
            )
        )

        val result = safeApiCall {
            throw exception
        }

        assertTrue(result is NetworkResult.Error)
        assertEquals(404, (result as NetworkResult.Error).code)
    }


    @Test
    fun `returns Error on generic Exception`() = runTest {
        val exception = Exception("Generic error")

        val result = safeApiCall {
            throw exception
        }

        assertTrue(result is NetworkResult.Error)
        assertEquals("Generic error", (result as NetworkResult.Error).message)
    }


    @Test
    fun `returns Error with null code on non-HttpException`() = runTest {
        val exception = Exception("Some error")

        val result = safeApiCall {
            throw exception
        }

        assertTrue(result is NetworkResult.Error)
        assertEquals(null, (result as NetworkResult.Error).code)
    }


    @Test
    fun `returns Success with complex data`() = runTest {
        data class User(val id: Int, val name: String)

        val result = safeApiCall {
            User(1, "Martin Gelevski")
        }

        assertTrue(result is NetworkResult.Success)
        val user = (result as NetworkResult.Success).data
        assertEquals(1, user.id)
        assertEquals("Martin Gelevski", user.name)
    }

}