package pro.aswin.member.routing

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import pro.aswin.utils.UUIDSerializer
import java.util.UUID

@Serializable
data class MemberResponse (
    val id: String,
    val shgId: Int?,
    val name: String?,
    val address: String?,
    val phoneNumber: String,
    val emailId: String?,
    val roleId: Int?,
)