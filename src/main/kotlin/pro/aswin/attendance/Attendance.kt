package pro.aswin.attendance

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.utils.ObjectIdSerializer
import java.time.LocalDate

data class Attendance(
    @BsonId val id: ObjectId = ObjectId(),
    @BsonProperty("shgId") val shgId: ObjectId,
    @BsonProperty("memberId") val memberId: ObjectId,
    @BsonProperty("date") val date: LocalDate,
    @BsonProperty("isPresent") val isPresent: Boolean
)
