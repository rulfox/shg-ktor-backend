package pro.aswin.shg.repository

import pro.aswin.exception.InsertionFailedException
import pro.aswin.exception.RequestedContentNotFoundException
import pro.aswin.shg.routing.CreateSelfHelpGroupRequest
import pro.aswin.shg.routing.CreateSelfHelpGroupResponse
import pro.aswin.shg.routing.GetSelfHelpGroupResponse

class SelfHelpGroupService(private val selfHelpGroupRepository: SelfHelpGroupRepository) {

    suspend fun createSelfHelpGroup(creatingMemberId: String, request: CreateSelfHelpGroupRequest): CreateSelfHelpGroupResponse {
        val createSelfHelpGroup = request.toDomain(creatingMemberId)
        val isCreated = selfHelpGroupRepository.createSelfHelpGroup(creatingMemberId, createSelfHelpGroup)
        if(isCreated){
            val createdShg = selfHelpGroupRepository.getSelfHelpGroupById(createSelfHelpGroup._id.toString())
            createdShg?.let {
                return it.toCreatedShgResponse()
            } ?: run {
                throw RequestedContentNotFoundException(responseCode = 400, errorReason = "Self Help Group created. Unable to get the details.")
            }
        } else {
            throw InsertionFailedException(responseCode = 400, errorReason = "Unable to create Self Help Group")
        }
    }

    suspend fun getSelfHelpGroupById(id: String): GetSelfHelpGroupResponse {
        val createdShg = selfHelpGroupRepository.getSelfHelpGroupById(id)
        createdShg?.let {
            return it.toGetShgResponse()
        } ?: run {
            throw RequestedContentNotFoundException(responseCode = 400, errorReason = "Unable to get the details.")
        }
    }
}