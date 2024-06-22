package pro.aswin.member

import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pro.aswin.exception.InsertionFailedException
import pro.aswin.exception.RequestedContentNotFoundException
import pro.aswin.exception.ValidationException
import pro.aswin.jwt.JwtService
import pro.aswin.member.routing.InsertMemberRequest
import pro.aswin.member.routing.LoginRequest
import pro.aswin.member.routing.LoginResponse
import pro.aswin.utils.Extensions.whenAllNotNull

class MemberService(private val jwtService: JwtService, private val repository: MemberRepository): KoinComponent {

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

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        val authenticatedMember = repository.login(loginRequest.phoneNumber, loginRequest.password)
        val generatedToken = jwtService.createJwtToken(request = loginRequest)
        authenticatedMember?.let { member ->
            generatedToken?.let { token ->
                member.token = token
            }
            return member
        } ?: run {
            throw ValidationException(responseCode = HttpStatusCode.Unauthorized.value, errorReason = "Invalid credentials")
        }
    }
}