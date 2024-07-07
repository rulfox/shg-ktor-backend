package pro.aswin.member.routing

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@Serializable
data class LoginResponse(
    @SerializedName("id") val id: String,
    @SerializedName("shgIds") val shgIds: List<@Contextual ObjectId>?= null,
    @SerializedName("name") val name: String?= null,
    @SerializedName("address") val address: String?= null,
    @SerializedName("phone") val phone: String?= null,
    @SerializedName("email") val email: String?= null,
    @SerializedName("token") var token: String?= null
)
