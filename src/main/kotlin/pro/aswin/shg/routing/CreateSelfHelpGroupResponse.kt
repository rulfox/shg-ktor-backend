package pro.aswin.shg.routing

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.utils.ObjectIdSerializer

@Serializable
data class CreateSelfHelpGroupResponse(
    @Serializable(with = ObjectIdSerializer::class)
    @BsonId
    @BsonProperty("id")
    val id: ObjectId,
    @BsonProperty("name")
    val name: String,
    @BsonProperty("address")
    val address: String,
    @BsonProperty("memberIds")
    val memberIds: List<String> ?= null,
    @Contextual
    @BsonProperty("secretaryId")
    val secretaryId: ObjectId ?= null,
    @Contextual
    @BsonProperty("presidentId")
    val presidentId: ObjectId ?= null
)