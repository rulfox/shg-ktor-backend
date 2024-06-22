package pro.aswin.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import pro.aswin.di.jwtModule
import pro.aswin.di.memberModule

fun Application.root() {
    install(Koin) {
        slf4jLogger()
        /*modules(
            memberModule,
            jwtModule
        )*/
    }
}