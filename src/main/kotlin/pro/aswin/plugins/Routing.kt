package pro.aswin.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pro.aswin.jwt.JwtService
import org.koin.ktor.ext.inject
import pro.aswin.jwt.jwtAuthenticationRoutes
import pro.aswin.member.presentation.memberRoutes
import pro.aswin.shg.repository.SelfHelpGroupService
import pro.aswin.shg.routing.shgRoutes

fun Application.configureRouting() {
    //val jwtService: JwtService by inject()
    //val selfHelpGroupService: SelfHelpGroupService by inject()
    routing {
        get("/") {
            call.respondText("Hello WorldX!")
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
        memberRoutes()
        //jwtAuthenticationRoutes(jwtService = jwtService)
        /*route("/api/shg"){
            shgRoutes(selfHelpGroupService = selfHelpGroupService)
        }*/
    }
}
