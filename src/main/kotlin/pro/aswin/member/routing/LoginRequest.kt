package pro.aswin.member.routing

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import pro.aswin.member.Member
import pro.aswin.member.routing.MemberResponse

@Serializable
data class LoginRequest(
    val phoneNumber: String,
    val password: String
)