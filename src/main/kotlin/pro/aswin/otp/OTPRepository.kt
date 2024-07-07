package pro.aswin.otp

import org.bson.types.ObjectId

class OTPRepository(private val dataSource: OTPDataSource) {
    suspend fun createOTP(otp: OTP): OTP = dataSource.createOTP(otp)
    suspend fun getOTPByCode(otpCode: String): OTP? = dataSource.getOTPByCode(otpCode)
    suspend fun validateOTP(request: ValidateOtpRequest): OTP? = dataSource.validateOTP(request)
    suspend fun deleteOTP(id: String): Boolean = dataSource.deleteOTP(id)
    suspend fun generateOTP(): String = dataSource.generateOTP()
    suspend fun generateOTPExpiryTime(): Long = dataSource.generateOTPExpiryTime()
}