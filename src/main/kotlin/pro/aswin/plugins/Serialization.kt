package pro.aswin.plugins

import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        val converter = KotlinxSerializationConverter(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })
        register(Json, converter)
        register(ContentType.Application.OctetStream, converter)

    }
}
