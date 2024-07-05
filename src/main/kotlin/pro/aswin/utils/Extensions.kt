package pro.aswin.utils

import io.ktor.server.application.*
import kotlin.reflect.KProperty1

object Extensions {
    fun <T: Any, R: Any> Collection<T?>.whenAllNotNull(block: (List<T>)->R) {
        if (this.all { it != null }) {
            block(this.filterNotNull()) // or do unsafe cast to non null collection
        }
    }

    fun <T: Any, R: Any> Collection<T?>.whenAnyNotNull(block: (List<T>)->R) {
        if (this.any { it != null }) {
            block(this.filterNotNull())
        }
    }

    fun Application.getConfigProperty(path: String): String{
        return environment.config.property(path = path).getString()
    }

    inline fun <reified T> KProperty1<T, *>.getVariableKeyName(): String {
        return this.name
    }
}