package pro.aswin.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse<out T> {
    @Serializable
    data class Success<out T>(
        val status: Int,
        val message: String? = null,
        val data: T?,
    ): ApiResponse<T>()

    @Serializable
    data class Error<out T>(
        val status: Int,
        val message: String? = null,
        val data: T?= null,
    ): ApiResponse<T>()
}