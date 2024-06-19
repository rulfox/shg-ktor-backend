package pro.aswin.member

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.member.routing.MemberResponse

import pro.aswin.utils.ObjectIdSerializer

@Serializable
data class Member @BsonCreator constructor(
    @Serializable(with = ObjectIdSerializer::class)
    @BsonId
    @BsonProperty("id") val id: ObjectId,
    @BsonProperty("shgId") val shgId: Int?,
    @BsonProperty("name") val name: String?,
    @BsonProperty("address") val address: String?,
    @BsonProperty("phoneNumber") val phoneNumber: String,
    @BsonProperty("emailId") val emailId: String?,
    @BsonProperty("roleId") val roleId: Int?,
    @BsonProperty("password") val password: String
){
    fun toResponse() = MemberResponse(
        id = id.toString(),
        shgId = shgId,
        name = name,
        address = address,
        phoneNumber = phoneNumber,
        emailId = emailId,
        roleId = roleId
    )
}