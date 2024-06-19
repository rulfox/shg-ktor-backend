package pro.aswin.member

import io.ktor.http.*
import pro.aswin.exception.InsertionFailedException
import pro.aswin.exception.RequestedContentNotFoundException
import pro.aswin.member.routing.InsertMemberRequest

class MemberService(private val repository: MemberRepository) {
    suspend fun getMemberByPhoneNumber(phoneNumber: String): Member {
        val expectedMember = repository.getMemberByPhoneNumber(phoneNumber)
        expectedMember?.let {
            return expectedMember
        } ?: run {
            throw RequestedContentNotFoundException(responseCode = 400, errorReason = "Member not found")
        }
    }

    suspend fun getMemberById(id: String): Member {
        val expectedMember = repository.getMemberById(id)
        expectedMember?.let {
            return expectedMember
        } ?: run {
            throw RequestedContentNotFoundException(responseCode = 400, errorReason = "Member not found")
        }
    }

    suspend fun registerMember(memberRequest: InsertMemberRequest): Member {
        val existingUserWithSamePhoneNumber = repository.getMemberByPhoneNumber(memberRequest.phoneNumber)
        existingUserWithSamePhoneNumber?.let {
            throw InsertionFailedException(responseCode = HttpStatusCode.Conflict.value, errorReason = "Unable to insert member since the member is already registered")
        } ?: run {
            val memberToInsert = memberRequest.toDomain()
            val isMemberInserted = repository.insertMember(memberToInsert)
            if(isMemberInserted){
                return memberToInsert
            } else {
                throw InsertionFailedException(responseCode = 400, errorReason = "Unable to insert member")
            }
        }
    }
}