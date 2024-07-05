package pro.aswin.member.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import pro.aswin.member.mvvm.MemberRepository
import pro.aswin.utils.ApiResponse

class DeleteMemberUseCase(private val memberRepository: MemberRepository) {
    suspend fun execute(memberId: ObjectId): ApiResponse<Boolean> = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = memberRepository.deleteMember(memberId)
            if (result) {
                ApiResponse.Success(status = 200, message = "Member deleted successfully.", data = result)
            } else {
                ApiResponse.Error(status = 404, message = "Member not found.")
            }
        } catch (e: Exception) {
            ApiResponse.Error(status = 500, message = e.localizedMessage)
        }
    }
}