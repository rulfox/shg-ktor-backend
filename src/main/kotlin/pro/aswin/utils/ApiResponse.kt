package pro.aswin.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResponse<out T> {
    @Serializable
    data class Success<out T>(
        val data: T?,
        val status: Int,
        val message: String? = null
    ): ApiResponse<T>()

    @Serializable
    data class Error<out T>(
        val data: T?= null,
        val status: Int,
        val message: String? = null
    ): ApiResponse<T>()
}