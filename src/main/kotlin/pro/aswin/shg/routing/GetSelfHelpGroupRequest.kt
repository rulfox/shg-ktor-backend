package pro.aswin.shg.routing

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.utils.ObjectIdSerializer

@Serializable
data class GetSelfHelpGroupRequest(
    @BsonProperty("id")
    val id: String?,
)