package pro.aswin.shg.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pro.aswin.exception.EntityNotFoundException
import pro.aswin.member.MemberService
import pro.aswin.shg.repository.SelfHelpGroupService
import pro.aswin.utils.ApiResponse

fun Route.shgRoutes(selfHelpGroupService: SelfHelpGroupService){
    post("/create") {
        val insertShgRequest = call.receiveNullable<CreateSelfHelpGroupRequest>()
        val creatingMemberId = call.request.headers["memberId"]?:""
        when {
            insertShgRequest == null -> {
                throw EntityNotFoundException(responseCode = HttpStatusCode.BadRequest.value, errorReason = "request body not found")
            }
            insertShgRequest.name.isEmpty() ->{
                throw EntityNotFoundException(responseCode = HttpStatusCode.BadRequest.value, errorReason = "Field ${CreateSelfHelpGroupRequest::name.name} not found")
            }
            insertShgRequest.address.isEmpty() ->{
                throw EntityNotFoundException(responseCode = HttpStatusCode.BadRequest.value, errorReason = "Field ${CreateSelfHelpGroupRequest::address.name} not found")
            }
            call.request.headers["memberId"].isNullOrEmpty() -> {
                throw EntityNotFoundException(responseCode = HttpStatusCode.BadRequest.value, errorReason = "Field memberId not found")
            }
            else -> {
                val createdShg = selfHelpGroupService.createSelfHelpGroup(creatingMemberId, insertShgRequest)
                call.respond(ApiResponse.Success(data = createdShg, status = HttpStatusCode.Created.value, message = "Self Help Group successfully created"))
            }
        }
    }

    post("/getShgById") {
        val getShgRequest = call.receive<GetSelfHelpGroupRequest>()
        if(getShgRequest.id.isNullOrEmpty()){
            throw EntityNotFoundException(responseCode = HttpStatusCode.BadRequest.value, errorReason = "Field ${GetSelfHelpGroupRequest::id.name} not found")
        } else {
            val authenticatedMember = selfHelpGroupService.getSelfHelpGroupById(getShgRequest.id)
            call.respond(ApiResponse.Success(data = authenticatedMember, status = HttpStatusCode.OK.value, message = "Member successfully registered"))
        }
    }
}