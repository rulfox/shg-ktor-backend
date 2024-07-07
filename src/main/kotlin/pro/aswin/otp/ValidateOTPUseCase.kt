package pro.aswin.otp

import org.bson.types.ObjectId
import pro.aswin.utils.ApiResponse

class ValidateOTPUseCase(private val otpRepository: OTPRepository) {
    suspend fun execute(request: ValidateOtpRequest): ApiResponse<Boolean> {
        return try {
            val otp = otpRepository.validateOTP(request)
            otp?.let {
                val otpDeleted = otpRepository.deleteOTP(it.otpCode)
                ApiResponse.Success(status = 200, message = "OTP validated successfully.", data = true)
            } ?: run {
                ApiResponse.Error(status = 400, message = "Invalid or expired OTP.")
            }
        } catch (e: Exception) {
            ApiResponse.Error(status = 500, message = e.localizedMessage)
        }
    }
}