package pro.aswin.otp

import org.bson.codecs.pojo.annotations.BsonProperty

data class ValidateOtpRequest(
    @BsonProperty("id") val id: String?,
    @BsonProperty("memberId") val memberId: String?,
    @BsonProperty("otpCode") val otpCode: String?,
    @BsonProperty("expiryTimeThreshold") val expiryTimeThreshold: Long = System.currentTimeMillis()
)