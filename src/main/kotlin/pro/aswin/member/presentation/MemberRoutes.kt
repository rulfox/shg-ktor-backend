package pro.aswin.member.presentation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject
import pro.aswin.member.data.Member
import pro.aswin.member.domain.DeleteMemberUseCase
import pro.aswin.member.domain.GetMemberUseCase
import pro.aswin.member.domain.RegisterMemberUseCase
import pro.aswin.member.domain.UpdateMemberUseCase
import pro.aswin.member.routing.MemberRegisterRequest
import pro.aswin.otp.ValidateOTPUseCase
import pro.aswin.utils.ApiResponse

fun Route.memberRoutes() {

    val registerMemberUseCase: RegisterMemberUseCase by inject()
    val validateOTPUseCase: ValidateOTPUseCase by inject()
    val deleteMemberUseCase: DeleteMemberUseCase by inject()
    val updateMemberUseCase: UpdateMemberUseCase by inject()
    val getMemberUseCase: GetMemberUseCase by inject()

    route("/members") {

        post("/register") {
            val member = call.receive<MemberRegisterRequest>()
            val response = registerMemberUseCase.execute(member)
            handleResponse(call, response)
        }

        post("/validate-otp") {
            val params = call.receive<Parameters>()
            val otpCode = params["otpCode"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing otpCode")
            val memberId = params["memberId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing memberId")
            val response = validateOTPUseCase.execute(otpCode, memberId)
            handleResponse(call, response)
        }

        delete("/{id}") {
            val memberId = call.parameters["id"]?.let { ObjectId(it) } ?: return@delete call.respond(HttpStatusCode.BadRequest, ApiResponse.Error<Unit>(status = HttpStatusCode.BadRequest.value, message = "Missing or malformed memberId"))
            val response = deleteMemberUseCase.execute(memberId)
            handleResponse(call, response)
        }

        put("/{id}") {
            val memberId = call.parameters["id"]?.let { ObjectId(it) } ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing or malformed memberId")
            val member = call.receive<Member>().copy(id = memberId)
            val response = updateMemberUseCase.execute(member)
            handleResponse(call = call, response = response)
        }

        get("/{id}") {
            val memberId = call.parameters["id"]?.let { ObjectId(it) } ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing or malformed memberId")
            val response = getMemberUseCase.execute(memberId)
            handleResponse(call, response)
        }
    }
}

private suspend fun <T> handleResponse(call: ApplicationCall, response: ApiResponse<T>) {
    when (response) {
        is ApiResponse.Success -> call.respond(HttpStatusCode.OK, response)
        is ApiResponse.Error -> call.respond(HttpStatusCode.fromValue(response.status), response)
    }
}
