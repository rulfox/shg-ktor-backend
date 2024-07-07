package pro.aswin.otp

import org.bson.types.ObjectId

interface OTPDataSource {
    suspend fun createOTP(otp: OTP): OTP
    suspend fun getOTPByCode(otpCode: String): OTP?
    suspend fun validateOTP(request: ValidateOtpRequest): OTP?
    suspend fun deleteOTP(id: String): Boolean
    suspend fun generateOTP(): String
    suspend fun generateOTPExpiryTime(): Long
}