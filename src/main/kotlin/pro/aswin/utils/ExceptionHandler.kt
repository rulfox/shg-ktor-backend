package pro.aswin.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import pro.aswin.exception.*

object ExceptionHandler {
    suspend fun handle(
        call: ApplicationCall,
        cause: Throwable,
        debugMode: Boolean  // In debug mode status 500 has more info in response. Do not use in production.
    ) {
        when (cause) {
            is EntityNotFoundException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = cause.responseCode, message = cause.errorReason)
                )
            }

            is BusinessException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = cause.responseCode, message = cause.errorReason)
                )
            }

            is MissingParameterException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = cause.responseCode, message = cause.errorReason)
                )
            }

            is RequestedContentNotFoundException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = cause.responseCode, message = cause.errorReason)
                )
            }

            is InsertionFailedException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = cause.responseCode, message = cause.errorReason)
                )
            }

            else -> {
                if (debugMode) {
                    // Printout stacktrace on console
                    cause.stackTrace.forEach { println(it) }
                    call.respondText(text = cause.message?:"500: $cause", status = HttpStatusCode.InternalServerError)
                } else {
                    cause.stackTrace.forEach { println(it) }
                    call.respond(
                        ApiResponse.Error<Nothing>(status = HttpStatusCode.InternalServerError.value, message = "Internal Error")
                    )
                }
            }
        }
    }
}