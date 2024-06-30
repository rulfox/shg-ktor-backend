package pro.aswin.member.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pro.aswin.exception.EntityNotFoundException
import pro.aswin.member.MemberService
import pro.aswin.utils.ApiResponse

fun Route.memberRoutes(memberService: MemberService){
    route("/member"){
        post("/register") {
            val insertMemberRequest = call.receive<InsertMemberRequest>()
            when {
                insertMemberRequest.phoneNumber.isEmpty() ->{
                    throw EntityNotFoundException(responseCode = HttpStatusCode.BadRequest.value, errorReason = "Field phoneNumber not found")
                }
                insertMemberRequest.password.isEmpty() ->{
                    throw EntityNotFoundException(responseCode = HttpStatusCode.BadRequest.value, errorReason = "Field password not found")
                }
            }
            val insertedMember = memberService.registerMember(insertMemberRequest)
            call.respond(ApiResponse.Success(data = insertedMember, status = HttpStatusCode.Created.value, message = "Member successfully registered"))
        }

        get("/getMemberById") {
            val member = memberService.getMemberById("667257bccf680e5df1653565")
            call.respond(ApiResponse.Success(data = member, status = HttpStatusCode.Created.value, message = "Member successfully registered"))
        }
    }

    route("/authentication"){
        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            when {
                loginRequest.phoneNumber.isEmpty() ->{
                    throw EntityNotFoundException(responseCode = HttpStatusCode.BadRequest.value, errorReason = "Field phoneNumber not found")
                }
                loginRequest.password.isEmpty() ->{
                    throw EntityNotFoundException(responseCode = HttpStatusCode.BadRequest.value, errorReason = "Field password not found")
                }
            }
            val authenticatedMember = memberService.login(loginRequest)
            call.respond(ApiResponse.Success(data = authenticatedMember, status = HttpStatusCode.OK.value, message = "Member successfully registered"))
        }
    }
}