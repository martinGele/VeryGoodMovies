package com.good.movies.core.util

import retrofit2.HttpException
import java.io.IOException

/**
 * A generic function to safely make API calls and handle exceptions.
 */
suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T
): NetworkResult<T> {
    return try {
        NetworkResult.Success(apiCall())
    } catch (e: HttpException) {
        NetworkResult.Error(
            message = e.message(),
            code = e.code(),
            throwable = e
        )
    } catch (e: IOException) {
        NetworkResult.Error(
            message = "Network error. Please check your connection.",
            throwable = e
        )
    } catch (e: Exception) {
        NetworkResult.Error(
            message = e.localizedMessage ?: "Unexpected error",
            throwable = e
        )
    }
}
