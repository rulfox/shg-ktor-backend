package pro.aswin.utils

sealed class ApiResponse<out T> {
    data class Success<out T>(
        val data: T?= null,
        val status: Int,
        val message: String? = null
    ): ApiResponse<T>()

    data class Error<out T>(
        val data: T?= null,
        val status: Int,
        val message: String? = null
    ): ApiResponse<T>()
}