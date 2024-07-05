package pro.aswin.member.routing

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.member.data.Member

@Serializable
data class MemberRegisterRequest (
    @BsonProperty("email") val email: String,
){
    fun toDomain(): Member{
        return Member(
            id = ObjectId(),
            phone = null,
            email = email,
            passwordHash = null,
            name = null,
            address = null,
            shgIds = null,
            registeredAt = System.currentTimeMillis()
        )
    }
}