package pro.aswin.shg.routing

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.utils.ObjectIdSerializer


data class GetSelfHelpGroupResponse(
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
    @BsonProperty("secretaryId")
    val secretaryId: ObjectId ?= null,
    @BsonProperty("presidentId")
    val presidentId: ObjectId ?= null,
    @BsonProperty("treasurerId")
    val treasurerId: ObjectId ?= null,
)