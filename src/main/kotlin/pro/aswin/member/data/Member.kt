package pro.aswin.member.data

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.member.routing.LoginResponse
import pro.aswin.utils.ObjectIdSerializer

data class Member @BsonCreator constructor(
    @BsonId @BsonProperty("id") val id: ObjectId,
    @BsonProperty("name") val name: String?,
    @BsonProperty("phone") val phone: String?,
    @BsonProperty("email") val email: String,
    @BsonProperty("address") val address: String?,
    @BsonProperty("shgIds") val shgIds: List<ObjectId>?,
    @BsonProperty("passwordHash") val passwordHash: String?,
    @BsonProperty("registeredAt") val registeredAt: Long = System.currentTimeMillis()
) {
    fun toLoginResponse() = LoginResponse(
        id = id.toString(),
        shgIds = shgIds,
        name = name,
        address = address,
        phone = phone,
        email = email,
        token = null
    )
}