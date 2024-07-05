package pro.aswin.member.routing

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import pro.aswin.member.data.Member
import pro.aswin.member.routing.MemberResponse

@Serializable
data class InsertMemberRequest(
    val shgId: String?= null,
    val name: String?= null,
    val address: String?= null,
    val phone: String,
    val email: String
){
    fun toDomain() = Member(
        id = ObjectId(),
        shgIds = shgId?.let { arrayListOf(ObjectId(it)) } ?: listOf() ,
        name = name,
        address = address,
        phone = phone,
        email = email,
        passwordHash = null,
    )
}