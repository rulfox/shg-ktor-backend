package pro.aswin.plugins

import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.serializer
import org.bson.types.ObjectId
import pro.aswin.member.data.Member
import pro.aswin.utils.*
import java.text.DateFormat

@OptIn(InternalSerializationApi::class)
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
            registerTypeAdapter(ObjectId::class.java, ObjectIdSerializer)
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }

        /*val apiResponseModule = SerializersModule {
            polymorphic(ApiResponse::class) {
                subclass(Success.serializer(AnySerializer))
                subclass(Error.serializer(AnySerializer))
            }
        }

        val combinedModule = SerializersModule {
            include(apiResponseModule)
            contextual(ObjectId::class, ObjectIdSerializer)
        }

        val genJson = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
            serializersModule = combinedModule
            encodeDefaults = true
        }
        json(genJson)*/
    }
}
