package pro.aswin.member.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.aswin.member.data.Member
import pro.aswin.member.mvvm.MemberRepository
import pro.aswin.utils.ApiResponse

class UpdateMemberUseCase(private val memberRepository: MemberRepository) {
    suspend fun execute(member: Member): ApiResponse<Member> = withContext(Dispatchers.IO) {
        return@withContext try {
            val updatedMember = memberRepository.updateMember(member)
            if (updatedMember != null) {
                ApiResponse.Success(status = 200, message = "Member updated successfully.", data = updatedMember)
            } else {
                ApiResponse.Error(status = 404, message = "Member not found.")
            }
        } catch (e: Exception) {
            ApiResponse.Error(status = 500, message = e.localizedMessage)
        }
    }
}