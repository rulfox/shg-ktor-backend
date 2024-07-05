package pro.aswin.otp

import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class OTP @BsonCreator constructor(
    @BsonId val id: ObjectId = ObjectId(),
    @BsonProperty("memberId") val memberId: ObjectId,
    @BsonProperty("otpCode") val otpCode: String,
    @BsonProperty("expiryTime") val expiryTime: Long
)