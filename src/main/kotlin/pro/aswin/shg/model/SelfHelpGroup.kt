package pro.aswin.shg.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.shg.routing.CreateSelfHelpGroupResponse
import pro.aswin.shg.routing.GetSelfHelpGroupResponse
import pro.aswin.utils.ObjectIdSerializer

@Serializable
data class SelfHelpGroup @BsonCreator constructor(
    @Serializable(with = ObjectIdSerializer::class)
    @BsonId
    @BsonProperty("id")
    val id: ObjectId,
    @BsonProperty("name")
    val name: String,
    @BsonProperty("address")
    val address: String,
    @Contextual
    @BsonProperty("memberIds")
    val memberIds: List<String> ?= null,
    @Contextual
    @BsonProperty("secretaryId")
    val secretaryId: ObjectId ?= null,
    @Contextual
    @BsonProperty("presidentId")
    val presidentId: ObjectId ?= null
) {
    fun toCreatedShgResponse(): CreateSelfHelpGroupResponse {
        return CreateSelfHelpGroupResponse(
            id = id,
            name = name,
            address = address,
            memberIds = memberIds?.map { it.toString() },
            secretaryId = secretaryId,
            presidentId = presidentId
        )
    }

    fun toGetShgResponse(): GetSelfHelpGroupResponse {
        return GetSelfHelpGroupResponse(
            id = id,
            name = name,
            address = address,
            memberIds = memberIds?.map { it.toString() },
            secretaryId = secretaryId,
            presidentId = presidentId
        )
    }
}