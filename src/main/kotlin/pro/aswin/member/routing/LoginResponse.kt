package pro.aswin.member.routing

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@Serializable
data class LoginResponse(
    @BsonProperty("id") val id: String,
    @BsonProperty("shgIds") val shgIds: List<@Contextual ObjectId>?,
    @BsonProperty("name") val name: String?,
    @BsonProperty("address") val address: String?,
    @BsonProperty("phone") val phone: String?,
    @BsonProperty("email") val email: String?,
    @BsonProperty("token") var token: String?
)
