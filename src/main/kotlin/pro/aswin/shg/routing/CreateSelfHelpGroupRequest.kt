package pro.aswin.shg.routing

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.shg.model.SelfHelpGroup

@Serializable
data class CreateSelfHelpGroupRequest(
    @BsonProperty("name")
    val name: String,
    @BsonProperty("address")
    val address: String,
    @BsonProperty("memberId")
    val memberId: String
){
    fun toDomain(memberId: String) = SelfHelpGroup(
        id = ObjectId(),
        name = name,
        address = address,
        memberIds = listOf(ObjectId(memberId)) ?: arrayListOf(),
        presidentId = ObjectId(memberId),
        secretaryId = null
    )
}