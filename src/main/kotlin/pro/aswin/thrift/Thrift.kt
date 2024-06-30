package pro.aswin.thrift

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.math.BigDecimal
import java.time.LocalDate

@Serializable
data class Thrift(
    @Contextual @BsonId val id: ObjectId = ObjectId(),
    @Contextual @BsonProperty("shgId") val shgId: ObjectId,
    @Contextual @BsonProperty("memberId") val memberId: ObjectId,
    @Contextual @BsonProperty("amount") val amount: BigDecimal,
    @Contextual @BsonProperty("date") val date: LocalDate
)
