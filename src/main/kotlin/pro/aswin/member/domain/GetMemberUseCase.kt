package pro.aswin.member.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import pro.aswin.member.data.Member
import pro.aswin.member.mvvm.MemberRepository
import pro.aswin.utils.ApiResponse

class GetMemberUseCase(private val memberRepository: MemberRepository) {
    suspend fun execute(memberId: ObjectId): ApiResponse<Member> = withContext(Dispatchers.IO) {
        return@withContext try {
            val member = memberRepository.getMember(memberId)
            if (member != null) {
                ApiResponse.Success(status = 200, message = "Member fetched successfully.", data = member)
            } else {
                ApiResponse.Error(status = 404, message = "Member not found.")
            }
        } catch (e: Exception) {
            ApiResponse.Error(status = 500, message = e.localizedMessage)
        }
    }
}