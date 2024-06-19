package pro.aswin.member.routing

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import pro.aswin.member.Member
import pro.aswin.member.routing.MemberResponse

@Serializable
data class InsertMemberRequest(
    val shgId: Int?= null,
    val name: String?= null,
    val address: String?= null,
    val phoneNumber: String,
    val emailId: String?= null,
    val roleId: Int?= null,
    val password: String
){
    fun toDomain() = Member(
        id = ObjectId(),
        shgId = shgId,
        name = name,
        address = address,
        phoneNumber = phoneNumber,
        emailId = emailId,
        roleId = roleId,
        password = password
    )
}