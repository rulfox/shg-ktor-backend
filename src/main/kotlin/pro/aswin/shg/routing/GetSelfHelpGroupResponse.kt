package pro.aswin.shg.routing

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId


@Serializable
data class GetSelfHelpGroupResponse(
    @Contextual
    @BsonId
    @BsonProperty("id")
    val id: ObjectId,
    @BsonProperty("name")
    val name: String,
    @BsonProperty("address")
    val address: String,
    @BsonProperty("memberIds")
    @Contextual
    val memberIds: List<String> ?= null,
    @Contextual
    @BsonProperty("secretaryId")
    val secretaryId: ObjectId ?= null,
    @Contextual
    @BsonProperty("presidentId")
    val presidentId: ObjectId ?= null
)