package pro.aswin.attendance

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.time.LocalDate

@Serializable
data class Attendance(
    @Contextual @BsonId val id: ObjectId = ObjectId(),
    @Contextual @BsonProperty("shgId") val shgId: ObjectId,
    @Contextual @BsonProperty("memberId") val memberId: ObjectId,
    @Contextual @BsonProperty("date") val date: LocalDate,
    @BsonProperty("isPresent") val isPresent: Boolean
)
