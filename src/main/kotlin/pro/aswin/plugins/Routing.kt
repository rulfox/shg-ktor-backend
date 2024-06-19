package pro.aswin.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pro.aswin.member.MemberService
import pro.aswin.member.routing.memberRoutes

fun Application.configureRouting(memberService: MemberService) {
    routing {
        get("/") {
            call.respondText("Hello WorldX!")
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
        memberRoutes(memberService)
    }
}
