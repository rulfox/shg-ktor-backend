package pro.aswin.otp

import pro.aswin.utils.ApiResponse

class ValidateOTPUseCase(private val otpRepository: OTPRepository) {
    suspend fun execute(otpCode: String, memberId: String): ApiResponse<Boolean> {
        return try {
            val otp = otpRepository.getOTPByCode(otpCode)
            if (otp != null && otp.memberId.toString() == memberId && otp.expiryTime > System.currentTimeMillis()) {
                otpRepository.deleteOTP(otpCode)
                ApiResponse.Success(status = 200, message = "OTP validated successfully.", data = true)
            } else {
                ApiResponse.Error(status = 400, message = "Invalid or expired OTP.")
            }
        } catch (e: Exception) {
            ApiResponse.Error(status = 500, message = e.localizedMessage)
        }
    }
}