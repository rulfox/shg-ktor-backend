package pro.aswin.otp

import org.bson.types.ObjectId

class OTPRepository(private val dataSource: OTPDataSource) {
    suspend fun createOTP(otp: OTP): OTP = dataSource.createOTP(otp)
    suspend fun getOTPByCode(otpCode: String): OTP? = dataSource.getOTPByCode(otpCode)
    suspend fun validateOTP(id: ObjectId, memberId: ObjectId, expiryTimeThreshold: Long): OTP? = dataSource.validateOTP(id, memberId, expiryTimeThreshold)
    suspend fun deleteOTP(otpCode: String): Boolean = dataSource.deleteOTP(otpCode)
    suspend fun generateOTP(): String = dataSource.generateOTP()
    suspend fun generateOTPExpiryTime(): Long = dataSource.generateOTPExpiryTime()
}