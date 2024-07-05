package pro.aswin.member.domain

import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import pro.aswin.member.mvvm.MemberRepository
import pro.aswin.member.routing.MemberRegisterRequest
import pro.aswin.otp.OTP
import pro.aswin.otp.OTPRepository
import pro.aswin.utils.ApiResponse

class RegisterMemberUseCase(private val memberRepository: MemberRepository, private val otpRepository: OTPRepository) {
    suspend fun execute(request: MemberRegisterRequest): ApiResponse<ObjectId> = withContext(Dispatchers.IO){
        try {
            memberRepository.getMemberByEmail(request.email)?.let {
                return@withContext ApiResponse.Error<ObjectId>(status = HttpStatusCode.InternalServerError.value, message = "Member already registered.")
            }?: run {
                val isMemberRegistered = memberRepository.registerMember(request.toDomain())
                if(isMemberRegistered){
                    val registeredMember = memberRepository.getMemberByEmail(request.email)
                    registeredMember?.let {
                        val otpGenerated = otpRepository.generateOTP()
                        val otpAuthenticationEntry = OTP(
                            otpCode = otpGenerated,
                            memberId = registeredMember.id,
                            id = ObjectId(),
                            expiryTime = otpRepository.generateOTPExpiryTime()
                        )
                        val createdOtp = otpRepository.createOTP(otpAuthenticationEntry)
                        return@withContext ApiResponse.Success<ObjectId>(status = HttpStatusCode.OK.value, message = "Member registered. Please verify otp sent -> ${registeredMember.email} to validate", data = createdOtp.id)
                    } ?: run {
                        return@withContext ApiResponse.Error<ObjectId>(status = HttpStatusCode.InternalServerError.value, message = "Member registered but unable to fetch member.")
                    }
                } else{
                    return@withContext ApiResponse.Error<ObjectId>(status = HttpStatusCode.InternalServerError.value, message = "Member creation failed.")
                }
            }
        } catch (e: Exception) {
            return@withContext ApiResponse.Error<ObjectId>(status = 500, message = e.localizedMessage)
        }
    }
}