package pro.aswin.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import pro.aswin.exception.*

object ExceptionHandler {
    suspend fun handle(
        call: ApplicationCall,
        exception: Throwable,
        debugMode: Boolean  // In debug mode status 500 has more info in response. Do not use in production.
    ) {
        when (exception) {
            is EntityNotFoundException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = exception.responseCode, message = exception.errorReason)
                )
            }

            is BusinessException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = exception.responseCode, message = exception.errorReason)
                )
            }

            is MissingParameterException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = exception.responseCode, message = exception.errorReason)
                )
            }

            is RequestedContentNotFoundException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = exception.responseCode, message = exception.errorReason)
                )
            }

            is InsertionFailedException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = exception.responseCode, message = exception.errorReason)
                )
            }

            is ValidationException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = exception.responseCode, message = exception.errorReason)
                )
            }

            is GeneralException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = exception.responseCode, message = exception.errorReason)
                )
            }

            is BadRequestException -> {
                call.respond(
                    ApiResponse.Error<Nothing>(status = HttpStatusCode.BadRequest.value, message = exception.cause?.message?: exception.message)
                )
            }

            else -> {
                if (debugMode) {
                    // Printout stacktrace on console
                    exception.stackTrace.forEach { println(it) }
                    call.respondText(text = exception.message?:"500: $exception", status = HttpStatusCode.InternalServerError)
                } else {
                    exception.stackTrace.forEach { println(it) }
                    call.respond(
                        ApiResponse.Error<Nothing>(status = HttpStatusCode.InternalServerError.value, message = "Internal Error")
                    )
                }
            }
        }
    }
}