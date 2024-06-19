package pro.aswin.plugins

import io.ktor.server.plugins.statuspages.*
import io.ktor.server.application.*
import pro.aswin.utils.ExceptionHandler

fun Application.configureExceptions() {
    val developmentMode = isDevelopmentMode()
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            ExceptionHandler.handle(call, cause, developmentMode)
        }
    }
}

fun Application.isDevelopmentMode(): Boolean {
    val environment = this.environment
    return environment.config.propertyOrNull("ktor.deployment.environment")?.getString() == "development" ||
            environment.config.propertyOrNull("ktor.deployment.development")?.getString()?.toBoolean() == true ||
            environment.developmentMode
}