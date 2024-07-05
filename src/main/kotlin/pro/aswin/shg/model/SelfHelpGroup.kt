package pro.aswin.shg.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.shg.routing.CreateSelfHelpGroupResponse
import pro.aswin.shg.routing.GetSelfHelpGroupResponse

@Serializable
data class SelfHelpGroup @BsonCreator constructor(
    @Contextual @BsonId val _id: ObjectId = ObjectId(),
    @BsonProperty("name") val name: String,
    @BsonProperty("address") val address: String = "",
    @BsonProperty("activeMemberIds") val activeMemberIds: List<@Contextual ObjectId>?,
    @BsonProperty("removedMemberIds") val removedMemberIds: List<@Contextual ObjectId>?,
    @Contextual @BsonProperty("presidentId") val presidentId: ObjectId?,
    @Contextual @BsonProperty("secretaryId") val secretaryId: ObjectId?,
    @Contextual @BsonProperty("treasurerId") val treasurerId: ObjectId?
){
    fun toCreatedShgResponse(): CreateSelfHelpGroupResponse {
        return CreateSelfHelpGroupResponse(
            id = _id,
            name = name,
            address = address,
            memberIds = activeMemberIds?.map { it.toString() },
            secretaryId = secretaryId,
            presidentId = presidentId,
            treasurerId = treasurerId
        )
    }

    fun toGetShgResponse(): GetSelfHelpGroupResponse {
        return GetSelfHelpGroupResponse(
            id = _id,
            name = name,
            address = address,
            memberIds = activeMemberIds?.map { it.toString() },
            secretaryId = secretaryId,
            presidentId = presidentId,
            treasurerId = treasurerId
        )
    }
}