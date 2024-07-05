package pro.aswin.loan

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import pro.aswin.utils.ObjectIdSerializer
import java.math.BigDecimal
import java.time.LocalDate

data class Loan(
    @BsonId val id: ObjectId = ObjectId(),
    @BsonProperty("shgId") val shgId: ObjectId,
    @BsonProperty("memberId") val memberId: ObjectId,
    @Contextual
    @BsonProperty("amount") val amount: BigDecimal,
    @Contextual
    @BsonProperty("date") val date: LocalDate,
    @BsonProperty("isRepaid") val isRepaid: Boolean = false
)
