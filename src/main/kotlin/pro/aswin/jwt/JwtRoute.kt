package pro.aswin.jwt

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pro.aswin.member.routing.LoginRequest
import pro.aswin.utils.ApiResponse

fun Route.jwtAuthenticationRoutes(jwtService: JwtService){
    route("/authentication"){
        post{
            val loginRequest = call.receive<LoginRequest>()
            val jwtToken = jwtService.createJwtToken(request = loginRequest)
            jwtToken?.let {
                call.respond(ApiResponse.Success(status = HttpStatusCode.OK.value, data = hashMapOf("token" to it), message = "Authenticated successfully"))
            } ?: run {
                call.respond(ApiResponse.Error(status = HttpStatusCode.Unauthorized.value, data = null, message = "Authentication failed"))
            }
        }
    }
}