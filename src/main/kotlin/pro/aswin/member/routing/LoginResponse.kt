package pro.aswin.member.routing

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonProperty

@Serializable
data class LoginResponse(
    @BsonProperty("id") val id: String,
    @BsonProperty("shgId") val shgId: Int?,
    @BsonProperty("name") val name: String?,
    @BsonProperty("address") val address: String?,
    @BsonProperty("phoneNumber") val phoneNumber: String,
    @BsonProperty("emailId") val emailId: String?,
    @BsonProperty("roleId") val roleId: Int?,
    @BsonProperty("token") var token: String?
)
